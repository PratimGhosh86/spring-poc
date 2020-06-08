package org.athome.app;

import static org.fissore.slf4j.FluentLoggerFactory.getLogger;

import java.util.Arrays;
import org.athome.app.jwt.JwtAuthenticationFilter;
import org.athome.app.jwt.JwtPostAuthenticationFilter;
import org.fissore.slf4j.FluentLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {

  private final FluentLogger log = getLogger(Security.class);

  private final Environment env;
  private final JwtPostAuthenticationFilter jwtPostAuthenticationFilter;

  @Override
  protected void configure(final HttpSecurity http) throws Exception { // since this is
    // for poc purpose, allow all path access
    http.authorizeRequests()
        .mvcMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/", "/v1/ns/**")
        .permitAll().anyRequest().authenticated().and()
        .addFilter(new JwtAuthenticationFilter(authenticationManager(), env.getProperty("jwt.key")))
        .addFilterAfter(jwtPostAuthenticationFilter, JwtAuthenticationFilter.class);
    // allow localhost access from different ports
    http.cors();
    // disable form login and csrf
    http.formLogin().disable();
    http.csrf().disable();
    // disable session
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    log.info().log("Configured Spring HTTP security");
  }

  @Override
  public void configure(final WebSecurity web) throws Exception {
    web.ignoring().mvcMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/",
        "/v1/ns/**");
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final CorsConfiguration corsConfig = new CorsConfiguration().applyPermitDefaultValues();
    corsConfig.setAllowCredentials(Boolean.TRUE);
    corsConfig.setAllowedMethods(
        Arrays.asList("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE"));
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfig);
    return source;
  }

}
