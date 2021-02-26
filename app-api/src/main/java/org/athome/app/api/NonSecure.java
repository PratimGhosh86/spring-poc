package org.athome.app.api;

import org.athome.app.domain.Login;
import org.athome.app.domain.Token;
import org.athome.app.service.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;

@CustomLog
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/v1/ns")
@CrossOrigin(origins = "*")
public class NonSecure {

  private final Authenticator authenticator;

  @GetMapping(path = "/welcome")
  public String welcome() {
    return "Hello";
  }

  @PostMapping(path = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Token authenticate(@RequestBody final Login login) {
    log.info().log("Authenticator {}", login.getId());
    return authenticator.authenticate(login);
  }

}
