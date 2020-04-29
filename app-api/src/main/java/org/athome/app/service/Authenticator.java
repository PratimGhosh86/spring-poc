package org.athome.app.service;

import static org.fissore.slf4j.FluentLoggerFactory.getLogger;
import org.fissore.slf4j.FluentLogger;
import org.springframework.stereotype.Service;

@Service
public class Authenticator {

  private final FluentLogger log = getLogger(Authenticator.class);

  public Boolean validateToken() {
    log.info().log("Validating token against datasource");
    return Boolean.TRUE;
  }

}
