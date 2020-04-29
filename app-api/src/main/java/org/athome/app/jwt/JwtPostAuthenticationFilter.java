package org.athome.app.jwt;

import static org.fissore.slf4j.FluentLoggerFactory.getLogger;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.athome.app.service.Authenticator;
import org.fissore.slf4j.FluentLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class JwtPostAuthenticationFilter extends GenericFilterBean {

  private final FluentLogger log = getLogger(JwtPostAuthenticationFilter.class);

  @Autowired
  private Authenticator auth;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    log.info().log("Verifying is JWT is still valid");
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    if (!auth.validateToken().booleanValue()) {
      log.info().log("Token is no longer valid");
      httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token rejected");
    }
    chain.doFilter(request, response);
  }

}
