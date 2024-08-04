package org.openclassrooms.mdd.security.service;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.openclassrooms.mdd.user.service.UserDetailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;


/**
 * Configuration class for security settings.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Value("${jwt.secret}")
  private String secretKey;

  private final UserDetailService userDetailServiceImpl;

  /**
   * Constructor for SecurityConfig.
   *
   * @param userDetailServiceImpl the user details service implementation
   */
  public SecurityConfig(@Lazy UserDetailService userDetailServiceImpl) {
    this.userDetailServiceImpl = userDetailServiceImpl;
  }

  private static final String[] WHITE_LIST_URL = {"/api/auth/login",
    "/api/auth/register", "/v2/api-docs", "/v3/api-docs",
    "/pictures/**", "/v3/api-docs/**", "/swagger-resources",
    "/swagger-resources/**", "/configuration/ui", "/swagger-ui/**", "/swagger-ui.html"
  };

  /**
   * Bean for password encoding.
   *
   * @return a BCryptPasswordEncoder instance
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Configures the security filter chain.
   *
   * @param http the HttpSecurity object
   * @return the configured SecurityFilterChain
   * @throws Exception if an error occurs during configuration
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .csrf(csrf -> csrf.disable())
      .cors(Customizer.withDefaults())
      .authorizeHttpRequests(ar -> ar.requestMatchers(WHITE_LIST_URL).permitAll())
      .authorizeHttpRequests(ar -> ar.anyRequest().authenticated())
      .oauth2ResourceServer(oa -> oa.jwt(Customizer.withDefaults()))
      .userDetailsService(userDetailServiceImpl)
      .build();
  }

  /**
   * Bean for JWT encoding.
   *
   * @return a JwtEncoder instance
   */
  @Bean
  public JwtEncoder jwtEncoder() {
    return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey.getBytes()));
  }

  /**
   * Bean for JWT decoding.
   *
   * @return a JwtDecoder instance
   */
  @Bean
  public JwtDecoder jwtDecoder() {
    SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
    return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS256).build();
  }

  /**
   * Bean for authentication management.
   *
   * @param userDetailsService the user details service
   * @return an AuthenticationManager instance
   */
  @Bean
  public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder());
    provider.setUserDetailsService(userDetailsService);
    return new ProviderManager(provider);
  }

  /**
   * Bean for CORS configuration.
   *
   * @return a CorsConfigurationSource instance
   */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.addAllowedOrigin("*");
    corsConfiguration.addAllowedMethod("*");
    corsConfiguration.addAllowedHeader("*");
    corsConfiguration.addAllowedOrigin("http://localhost:4200");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
    return source;
  }

}
