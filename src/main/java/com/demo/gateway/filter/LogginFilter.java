package com.demo.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LogginFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("[GLOBAL FILTER]Request: {} {}", exchange.getRequest().getMethod(), exchange.getRequest().getURI());
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            log.info("[GLOBAL FILTER]Response: {}", exchange.getResponse().getStatusCode());
        }));
    }
}
