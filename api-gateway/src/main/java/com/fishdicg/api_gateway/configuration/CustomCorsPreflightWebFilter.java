package com.fishdicg.api_gateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
public class CustomCorsPreflightWebFilter {

    @Bean
    public WebFilter corsPreflightWebFilter() {
        return (ServerWebExchange exchange, WebFilterChain chain) -> {
            exchange.getResponse().getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:4200");
            exchange.getResponse().getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE, OPTIONS");
            exchange.getResponse().getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Authorization, Content-Type, X-Requested-With");
            exchange.getResponse().getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");

            if (HttpMethod.OPTIONS.equals(exchange.getRequest().getMethod())) {
                exchange.getResponse().setStatusCode(HttpStatus.OK);
                return exchange.getResponse().setComplete();
            }

            return chain.filter(exchange);
        };
    }
}
