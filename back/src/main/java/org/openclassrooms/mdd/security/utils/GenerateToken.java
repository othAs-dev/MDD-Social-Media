package org.openclassrooms.mdd.security.utils;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class GenerateToken {

  private final JwtEncoder jwtEncoder;

  private static final int ACCESS_TOKEN_VALIDITY= 1440;  // Durée de validité des tokens en minutes

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
   * Générer un token JWT.
   *
   * @param authentication L'authentification de l'utilisateur.
   * @param validity       Durée de validité du token.
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
