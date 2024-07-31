package org.openclassrooms.mdd.security.utils;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class GenerateToken {

  private final JwtEncoder jwtEncoder;

  /**
   * Generate a token for the user.
   * @param authentication The user authentication.
   * @return The generated token.
   */
  public String generateTokenFunction(Authentication authentication) {
    Instant now = Instant.now();
    String scope = authentication.getAuthorities().stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.joining(" "));
    JwtClaimsSet claims = JwtClaimsSet.builder()
      .issuedAt(now)
      .expiresAt(now.plus(90, ChronoUnit.MINUTES))
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
