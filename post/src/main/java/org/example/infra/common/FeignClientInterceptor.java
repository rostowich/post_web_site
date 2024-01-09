package org.example.infra.common;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.example.infra.security.AuthorizationHeaderFilter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FeignClientInterceptor implements RequestInterceptor {

  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String TOKEN_TYPE = "Bearer";

  @Override
  public void apply(RequestTemplate requestTemplate) {
    var token = AuthorizationHeaderFilter.getNullableBearerToken();
    if (token != null ) {
      requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s", TOKEN_TYPE, token));
    }
  }
}