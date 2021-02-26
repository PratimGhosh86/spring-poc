package org.athome.app.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.athome.app.service.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;

@CustomLog
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class JwtPostAuthenticationFilter extends GenericFilterBean {

  private final Authenticator auth;

  @Override
  public void doFilter(final ServletRequest request, final ServletResponse response,
      final FilterChain chain) throws IOException, ServletException {
    log.info().log("Verifying is JWT is still valid");
    final var httpRequest = (HttpServletRequest) request;
    final var httpResponse = (HttpServletResponse) response;
    if (!auth.validateToken(httpRequest.getHeader("Authorization")).booleanValue()) {
      log.info().log("Token is no longer valid");
      httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token rejected");
    }
    chain.doFilter(request, response);
  }

}
