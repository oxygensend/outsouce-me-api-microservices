package com.oxygensened.gateway.filters;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayFilterConfiguration {


    @Bean
    @ConditionalOnProperty(value = "route-logging.enabled", havingValue = "true")
    RouteLoggingFilter routeLoggingFilter() {
        return new RouteLoggingFilter();
    }

    @Bean
    @ConditionalOnProperty(value = "correlation-id-filter.enabled", havingValue = "true")
    RequestCorrelationIdFilter requestCorrelationIdFilter() {
        return new RequestCorrelationIdFilter();
    }

    @Bean
    NoPathRoutePredicateFactory noPathRoutePredicateFactory() {
        return new NoPathRoutePredicateFactory();
    }
}
