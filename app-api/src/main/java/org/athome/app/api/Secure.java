package org.athome.app.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v3")
public class Secure {

  @GetMapping(path = "/welcome")
  public String welcome() {
    return "Hello";
  }

}
