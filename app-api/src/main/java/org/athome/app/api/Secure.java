package org.athome.app.api;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import org.athome.app.service.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;

@CustomLog
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/v1/sec")
@CrossOrigin(origins = "*")
public class Secure {

  private final Authenticator authenticator;

  @GetMapping(path = "/verify")
  public String verify(
      @RequestHeader(name = "Authorization", defaultValue = EMPTY) final String token,
      final Authentication auth) {
    log.info().log("Validating token for user {}", auth.getName());
    return authenticator.validateToken(auth, token);
  }

}
