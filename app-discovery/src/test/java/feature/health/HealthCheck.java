package feature.health;

import com.intuit.karate.junit5.Karate;

public class HealthCheck {

  @Karate.Test
  Karate testQuotes() {
    return Karate.run("health").relativeTo(getClass());
  }

}
