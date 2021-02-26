package org.athome.app;

import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import lombok.CustomLog;

@CustomLog
@SpringBootTest
@ActiveProfiles(profiles = {"test"})
class InitializerTests {

  @Autowired
  private Environment env;

  @Test
  void contextLoads() {
    log.info().log("Context loaded successfully");
    log.info().log("{}", env.getProperty("spring.application.name"));
    assertThat(TRUE).isTrue();
  }

}
