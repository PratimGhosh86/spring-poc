package org.athome.app.service;

import static java.util.stream.Collectors.joining;
import static org.fissore.slf4j.FluentLoggerFactory.getLogger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.athome.app.domain.Login;
import org.athome.app.domain.Token;
import org.fissore.slf4j.FluentLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class Authenticator {

  private final FluentLogger log = getLogger(Authenticator.class);

  private final Environment env;

  public Token authenticate(final Login login) {
    final Map<String, Object> claims = new HashMap<>();
    claims.put("id", login.getId());
    claims.put("source", "api");
    final LocalDateTime expiry = LocalDate.now().plusDays(1L).atStartOfDay();
    final String token = Jwts.builder().setSubject(login.getId()).setId(login.getId())
        .addClaims(claims)
        .setIssuedAt(
            Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))
        .setExpiration(Date.from(expiry.atZone(ZoneId.systemDefault()).toInstant()))
        .signWith(Keys.hmacShaKeyFor(env.getProperty("jwt.key").getBytes()),
            SignatureAlgorithm.HS256)
        .compact();
    log.info().log("Generated token");
    return Token.builder().jwt(token).expiry(expiry).build();
  }

  public Boolean validateToken(final String token) {
    log.info().log("Token: {}", token);
    return Boolean.TRUE;
  }

  public String validateToken(final Authentication auth, final String token) {
    log.info().log("Token: {}", token);
    log.info().log("Authorities: {}",
        auth.getAuthorities().stream().map(Object::toString).collect(joining(",")));
    return "Verified";
  }

}
