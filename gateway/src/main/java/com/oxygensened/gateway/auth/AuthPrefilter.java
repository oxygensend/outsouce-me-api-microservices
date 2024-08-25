package com.oxygensened.gateway.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import reactor.core.publisher.Mono;

@Component
public class AuthPrefilter extends AbstractGatewayFilterFactory<AuthPrefilter.Config> {

    private static final String AUTHORIZATION_ATTRIBUTE = "Authorization";
    private static final String AUTHORITIES_ATTRIBUTE = "X-AUTHORITIES";
    private static final String USER_ID_ATTRIBUTE = "X-USER-ID";
    private static final String AUTH_SERVICE_URI = "lb://auth-service/v1/auth/validate_token";
    private static final String FOR_YOU_QUERY = "FOR_YOU";
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthPrefilter.class);
    private final WebClient.Builder webClientBuilder;

    public AuthPrefilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String bearerToken = request.getHeaders().getFirst(AUTHORIZATION_ATTRIBUTE);

            if (isSecured(request, config.excludedPaths)) {

                if (bearerToken == null) {
                    throw new UnauthorizedException("Authorization header is missing");
                }

                return webClientBuilder.build()
                                       .post()
                                       .uri(AUTH_SERVICE_URI)
                                       .header(HttpHeaders.AUTHORIZATION, bearerToken)
                                       .retrieve().bodyToMono(AuthValidationResponse.class)
                                       .map(response -> addAuthHeaders(response, exchange))
                                       .flatMap(chain::filter)
                                       .onErrorResume(e -> {
                                           LOGGER.error("Error occurred", e);
                                           return Mono.error(new UnauthorizedException("Unauthorized", e));
                                       });
            }

            return chain.filter(exchange);
        }));
    }

    public static class Config {
        private List<String> excludedPaths = new ArrayList<>();

        public List<String> excludedPaths() {
            return excludedPaths;
        }

        public void setExcludedPaths(List<String> excludedPaths) {
            this.excludedPaths = excludedPaths;
        }
    }

    private ServerWebExchange addAuthHeaders(AuthValidationResponse response, ServerWebExchange exchange) {
        exchange.getRequest().mutate().header(USER_ID_ATTRIBUTE, response.userId());
        exchange.getRequest().mutate().header(AUTHORITIES_ATTRIBUTE, response.authorities()
                                                                             .stream()
                                                                             .map(GrantedAuthority::getAuthority)
                                                                             .map(s -> s.replaceAll("\"", ""))
                                                                             .toList()
                                                                             .toArray(new String[0]));
        return exchange;
    }

    private boolean isSecured(ServerHttpRequest request, List<String> excludedPaths) {
        return excludedPaths.stream()
                            .noneMatch(uri -> isPathNotSecured(uri, request));
    }

    private boolean isPathNotSecured(String path, ServerHttpRequest request) {
        String[] methodAndPath = path.split(",");
        boolean isMethodMatched = methodAndPath.length < 2 || methodAndPath[0].equals(request.getMethod().name());
        boolean isPathMatched = Pattern.matches(methodAndPath[methodAndPath.length - 1], request.getPath().value());
        boolean noForYouFunctionality;
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        noForYouFunctionality = !queryParams.containsKey("sort")
            || !Objects.equals(queryParams.getFirst("sort"), FOR_YOU_QUERY);
        return isMethodMatched && isPathMatched && noForYouFunctionality;
    }

}
