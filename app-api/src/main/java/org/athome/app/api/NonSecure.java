package org.athome.app.api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.athome.app.domain.Login;
import org.athome.app.domain.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/v1")
public class NonSecure {

  private final Environment env;

  @GetMapping(path = "/welcome")
  public String welcome() {
    return "Hello";
  }

  @PostMapping(path = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Token authenticate(@RequestBody final Login login) {
    final Map<String, Object> claims = new HashMap<>();
    claims.put("id", login.getId());
    final LocalDateTime expiry = LocalDate.now().plusDays(1L).atStartOfDay();
    return Token.builder().jwt(Jwts.builder().setSubject(login.getId()).setId(login.getId())
        .addClaims(claims)
        .setIssuedAt(
            Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))
        .setExpiration(Date.from(expiry.atZone(ZoneId.systemDefault()).toInstant()))
        .signWith(Keys.hmacShaKeyFor(env.getProperty("jwt.key").getBytes()),
            SignatureAlgorithm.HS256)
        .compact()).expiry(expiry).build();
  }

}
