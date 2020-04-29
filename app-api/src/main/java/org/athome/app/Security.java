package org.athome.app;

import static org.fissore.slf4j.FluentLoggerFactory.getLogger;
import org.fissore.slf4j.FluentLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
// @Configuration
// @EnableWebSecurity
public class Security { // extends WebSecurityConfigurerAdapter {


  private final FluentLogger log = getLogger(Security.class);

  private final Environment env;

  /*
   * @Override protected void configure(final HttpSecurity http) throws Exception { // since this is
   * for poc purpose, allow all path access
   * http.authorizeRequests().mvcMatchers("/v1**").permitAll().anyRequest().authenticated();
   * http.requiresChannel().antMatchers("/**").requiresSecure(); // allow localhost access from
   * different ports http.cors(); // disable form login and csrf http.formLogin().disable();
   * http.csrf().disable(); // disable session
   * http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // need to
   * allow H2 console if (Arrays.asList(env.getActiveProfiles()).contains("embedded")) {
   * http.headers().frameOptions().disable(); } log.info().log("Configured Spring HTTP security"); }
   */

  /*
   * @Bean public CorsConfigurationSource corsConfigurationSource() { final CorsConfiguration
   * corsConfig = new CorsConfiguration().applyPermitDefaultValues();
   * corsConfig.setAllowCredentials(Boolean.TRUE); corsConfig.setAllowedMethods(
   * Arrays.asList("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE")); final
   * UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
   * source.registerCorsConfiguration("/**", corsConfig); return source; }
   */

  /*
   * @Override public void configure(WebSecurity web) throws Exception {
   * web.ignoring().mvcMatchers("/v1**"); }
   */


}
