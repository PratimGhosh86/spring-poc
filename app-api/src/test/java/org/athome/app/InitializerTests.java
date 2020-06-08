package org.athome.app;

import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fissore.slf4j.FluentLoggerFactory.getLogger;

import org.fissore.slf4j.FluentLogger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = {"test"})
class InitializerTests {

  private final FluentLogger log = getLogger(InitializerTests.class);

  @Autowired
  private Environment env;

  @Test
  public void contextLoads() {
    log.info().log("Context loaded successfully");
    log.info().log("{}", env.getProperty("spring.application.name"));
    assertThat(TRUE).isTrue();
  }

}
