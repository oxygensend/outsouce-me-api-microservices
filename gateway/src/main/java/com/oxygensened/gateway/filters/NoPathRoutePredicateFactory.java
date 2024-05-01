package com.oxygensened.gateway.filters;

import java.util.function.Predicate;
import org.springframework.cloud.gateway.handler.predicate.PathRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class NoPathRoutePredicateFactory extends PathRoutePredicateFactory {

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return super.apply(config).negate();
    }
}