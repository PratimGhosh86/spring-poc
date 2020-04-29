package org.athome.app;

import static java.util.Locale.setDefault;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.of;
import static org.fissore.slf4j.FluentLoggerFactory.getLogger;
import static org.springframework.boot.SpringApplication.run;
import java.util.Locale;
import javax.annotation.PostConstruct;
import org.fissore.slf4j.FluentLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor_ = {@Autowired})
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class Initializer {

  private final FluentLogger log = getLogger(Initializer.class);

  private final Environment env;

  @PostConstruct
  public void setup() {
    setDefault(new Locale("en", "US"));
    log.warn().log("System locale set to en-US");
  }

  @EventListener(ApplicationReadyEvent.class)
  public void ready() {
    log.info().log("Gateway is ready to forward requests");
    log.warn().log("Active profiles: {}", of(env.getActiveProfiles()).collect(joining(",")));
  }

  public static void main(final String[] args) {
    run(Initializer.class, args);
  }

}
