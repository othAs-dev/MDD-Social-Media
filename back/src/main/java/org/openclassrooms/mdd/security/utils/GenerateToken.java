package org.openclassrooms.mdd.security.utils;

import lombok.AllArgsConstructor;
import org.openclassrooms.mdd.exceptions.ApiException;
import org.openclassrooms.mdd.user.entity.UserDetailEntity;
import org.openclassrooms.mdd.user.repository.UserDetailRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class GenerateToken {

  private final JwtEncoder jwtEncoder;
  private final JwtDecoder jwtDecoder; // Ajouter JwtDecoder pour décoder et valider les refresh tokens
  private final UserDetailRepository userDetailRepository; // Pour récupérer les détails de l'utilisateur à partir du refresh token

  // Durée de validité des tokens en minutes
  private static final int ACCESS_TOKEN_VALIDITY = 15; // 15 minutes
  private static final int REFRESH_TOKEN_VALIDITY = 1440; // 24 heures

  /**
   * Générer un token d'accès pour l'utilisateur.
   *
   * @param authentication L'authentification de l'utilisateur.
   * @return Le token d'accès généré.
   */
  public String generateAccessToken(Authentication authentication) {
    return generateToken(authentication, ACCESS_TOKEN_VALIDITY);
  }

  /**
   * Générer un token de rafraîchissement pour l'utilisateur.
   *
   * @param authentication L'authentification de l'utilisateur.
   * @return Le token de rafraîchissement généré.
   */
  public String generateRefreshToken(Authentication authentication) {
    return generateToken(authentication, REFRESH_TOKEN_VALIDITY);
  }

  /**
   * Rafraîchir le token d'accès à l'aide d'un token de rafraîchissement.
   *
   * @param refreshToken Le token de rafraîchissement.
   * @return Le nouveau token d'accès.
   * @throws ApiException.BadRequestException Si le token de rafraîchissement est invalide.
   */
  public String refreshAccessToken(String refreshToken) throws ApiException.BadRequestException {
    try {
      var jwt = jwtDecoder.decode(refreshToken); // Décoder le refresh token
      String username = jwt.getSubject();

      UserDetailEntity userEntity = userDetailRepository.findByUsername(username);

      if (userEntity == null) {
        throw new ApiException.BadRequestException("Invalid refresh token");
      }

      // Créer une nouvelle authentification à partir de l'utilisateur
      Authentication authentication = new UsernamePasswordAuthenticationToken(
              userEntity.getUsername(),
              userEntity.getPassword(),
              null
      );

      // Générer et retourner un nouveau token d'accès
      return generateAccessToken(authentication);

    } catch (Exception e) {
      throw new ApiException.BadRequestException("Failed to refresh access token: " + e.getMessage());
    }
  }

  /**
   * Générer un token JWT.
   *
   * @param authentication L'authentification de l'utilisateur.
   * @param validity Durée de validité du token.
   * @return Le token JWT généré.
   */
  private String generateToken(Authentication authentication, int validity) {
    Instant now = Instant.now();
    String scope = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));
    JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuedAt(now)
            .expiresAt(now.plus(validity, ChronoUnit.MINUTES)) // Utiliser la durée de validité passée
            .subject(authentication.getName())
            .claim("scope", scope)
            .build();
    JwtEncoderParameters parameters = JwtEncoderParameters.from(
            JwsHeader.with(MacAlgorithm.HS256).build(),
            claims
    );

    return jwtEncoder.encode(parameters).getTokenValue();
  }
}
