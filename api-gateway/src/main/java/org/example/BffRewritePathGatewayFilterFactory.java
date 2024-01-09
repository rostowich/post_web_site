package org.example;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.springframework.cloud.gateway.support.GatewayToStringStyler.filterToStringCreator;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;

@Component
public class BffRewritePathGatewayFilterFactory extends AbstractGatewayFilterFactory<BffRewritePathGatewayFilterFactory.Config> {

  /** Regexp key. */
  public static final String REGEXP_KEY = "regexp";

  /** Replacement key. */
  public static final String REPLACEMENT_KEY = "replacement";

  public BffRewritePathGatewayFilterFactory() {
    super(Config.class);
  }

  @Override
  public List<String> shortcutFieldOrder() {
    return Arrays.asList(REGEXP_KEY, REPLACEMENT_KEY);
  }

  @Override
  public GatewayFilter apply(Config config) {
    String replacement = config.replacement.replace("$\\", "$");
    return new GatewayFilter() {
      @Override
      public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var req = exchange.getRequest();
        addOriginalRequestUrl(exchange, req.getURI());
        var path = req.getURI().getRawPath();
        var newPath = path.replaceAll(config.regexp, replacement);
        var request = req.mutate().path(newPath).contextPath("/").build();
        exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, request.getURI());
        return chain.filter(exchange.mutate().request(request).build());
      }

      @Override
      public String toString() {
        return filterToStringCreator(BffRewritePathGatewayFilterFactory.this)
            .append(config.getRegexp(), replacement)
            .toString();
      }
    };
  }

  public static class Config {

    private String regexp;
    private String replacement;

    public String getRegexp() {
      return regexp;
    }

    public Config setRegexp(String regexp) {
      Assert.hasText(regexp, "regexp must have a value");
      this.regexp = regexp;
      return this;
    }

    public String getReplacement() {
      return replacement;
    }

    public Config setReplacement(String replacement) {
      Assert.notNull(replacement, "replacement must not be null");
      this.replacement = replacement;
      return this;
    }
  }
}
