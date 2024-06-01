package com.oxygensened.gateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

final class RequestCorrelationIdFilter implements GlobalFilter, Ordered {
    private final static String REQUEST_ID_HEADER = "Request-Id";
    private static final Logger LOG = LoggerFactory.getLogger(RequestCorrelationIdFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String logPrefix = exchange.getLogPrefix();
        ServerHttpRequest mutatedRequest = request.mutate()
                                                  .header(REQUEST_ID_HEADER, logPrefix)
                                                  .build();
        ServerWebExchange mutatedExchange = exchange.mutate()
                                                    .request(mutatedRequest)
                                                    .build();

        LOG.info("Request-Id for request {} {} is {}",
                 exchange.getRequest().getMethod(), exchange.getRequest().getURI(), logPrefix);

        return chain.filter(mutatedExchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}