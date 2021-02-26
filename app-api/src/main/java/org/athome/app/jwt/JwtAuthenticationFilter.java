package org.athome.app.jwt;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.CustomLog;

@CustomLog
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

  private final String key;

  public JwtAuthenticationFilter(final AuthenticationManager auth, final String key) {
    super(auth);
    this.key = key;
  }

  @Override
  protected void doFilterInternal(final HttpServletRequest req, final HttpServletResponse res,
      final FilterChain chain) throws IOException, ServletException {
    log.info().log("Validating Auth token");
    final var authHeader =
        Optional.ofNullable(req.getHeader("Authorization")).orElse(StringUtils.EMPTY);
    if (!authHeader.startsWith("Bearer ")) {
      chain.doFilter(req, res);
      return;
    }
    log.info().log("Expected token layout received, parsing");
    // parse the token
    final var jws = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(key.getBytes())).build()
        .parseClaimsJws(authHeader.replace("Bearer ", StringUtils.EMPTY));
    log.info().log("Token parsed, building security context");
    final var source =
        Optional.ofNullable(jws.getBody().get("source")).orElse(StringUtils.EMPTY).toString();
    if (!"api".equals(source))
      throw new JwtException("Invalid token");
    final var authentication = new UsernamePasswordAuthenticationToken(
        Optional.ofNullable(jws.getBody().get("id")).orElse(StringUtils.EMPTY).toString(), source,
        Arrays.asList(new SimpleGrantedAuthority("SELFSIGNED")));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    log.info().log("Security context built, continuing");
    chain.doFilter(req, res);
  }

}
