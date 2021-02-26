package org.athome.app;

import static java.util.Locale.setDefault;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.of;
import static org.springframework.boot.SpringApplication.run;
import java.util.Locale;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import lombok.AllArgsConstructor;
import lombok.CustomLog;

@AllArgsConstructor(onConstructor_ = {@Autowired})
@CustomLog
@SpringBootApplication
@EnableEurekaServer
public class Initializer {

  private final Environment env;

  @PostConstruct
  public void setup() {
    setDefault(new Locale("en", "US"));
    log.warn().log("System locale set to en-US");
  }

  @EventListener(ApplicationReadyEvent.class)
  public void ready() {
    log.info().log("Discovery is ready to register applications");
    log.warn().log("Active profiles: {}", of(env.getActiveProfiles()).collect(joining(",")));
  }

  public static void main(final String[] args) {
    run(Initializer.class, args);
  }

}
