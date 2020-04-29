package org.athome.app.api;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/sec")
@CrossOrigin(origins = "*")
public class Secure {

  @GetMapping(path = "/verify")
  public String verify(final Authentication auth) {
    return "Verified";
  }

}
