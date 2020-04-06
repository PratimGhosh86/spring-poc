package org.athome.app;

import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fissore.slf4j.FluentLoggerFactory.getLogger;
import org.fissore.slf4j.FluentLogger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class InitializerTests {

  private final FluentLogger log = getLogger(InitializerTests.class);

  @Test
  void contextLoads() {
    log.info().log("Context loaded successfully");
    assertThat(TRUE).isTrue();
  }

}
