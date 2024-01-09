package org.example.infra.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class AuthorizationHeaderFilter {

  private AuthorizationHeaderFilter() {
  }

  public static final String EMAIL_CLAIM_KEY = "sub";
  public static final String USER_ID_CLAIM_KEY = "id";

  public static ExchangeFilterFunction jwtFilterRequest() {
    return ExchangeFilterFunction.ofRequestProcessor(
            clientRequest ->
                    getBearerToken()
                            .map(
                                    token ->
                                            Mono.just(
                                                    ClientRequest.from(clientRequest)
                                                            .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                                                            .build()))
                            .orElseGet(() -> Mono.just(clientRequest)));
  }

  public static Optional<Jwt> getJwtPrincipal() {
    return Optional.ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .map(Authentication::getPrincipal)
            .map(Jwt.class::cast);
  }

  public static Optional<String> getBearerToken() {
    return getJwtPrincipal().map(Jwt::getTokenValue);
  }

  public static String getNullableBearerToken() {
    return getBearerToken().orElse(null);
  }

  public static String getEmailFromJwtPrincipal() {
    return getJwtPrincipal().map(jwt -> jwt.getClaim(EMAIL_CLAIM_KEY).toString()).orElse(null);
  }

  public static String getIdFromJwtPrincipal() {
    return getJwtPrincipal().map(jwt -> jwt.getClaim(USER_ID_CLAIM_KEY).toString()).orElse(null);
  }
}