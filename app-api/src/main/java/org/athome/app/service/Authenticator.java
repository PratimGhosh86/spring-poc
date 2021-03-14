package org.athome.app.service;

import static java.util.stream.Collectors.joining;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

import org.athome.app.domain.Login;
import org.athome.app.domain.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;

@CustomLog
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class Authenticator {

  private final Environment env;

  public Token authenticate(final Login login) {
    final var claims = new HashMap<String, Object>();
    claims.put("id", login.getId());
    claims.put("source", "api");
    final var expiry = LocalDate.now().plusDays(1L).atStartOfDay();
    final var token = Jwts.builder().setSubject(login.getId()).setId(login.getId()).addClaims(claims)
        .setIssuedAt(Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))
        .setExpiration(Date.from(expiry.atZone(ZoneId.systemDefault()).toInstant()))
        .signWith(Keys.hmacShaKeyFor(env.getProperty("jwt.key").getBytes()), SignatureAlgorithm.HS256).compact();
    log.info().log("Generated token");
    return Token.builder().jwt(token).expiry(expiry).build();
  }

  public Boolean validateToken(final String token) {
    log.info().log("Token: {}", token);
    return Boolean.TRUE;
  }

  public String validateToken(final Authentication auth, final String token) {
    log.info().log("Token: {}", token);
    log.info().log("Authorities: {}", auth.getAuthorities().stream().map(Object::toString).collect(joining(",")));
    return "Verified";
  }

}
