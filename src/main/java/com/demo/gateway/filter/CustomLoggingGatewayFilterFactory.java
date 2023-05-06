package com.demo.gateway.filter;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomLoggingGatewayFilterFactory extends AbstractGatewayFilterFactory<CustomLoggingGatewayFilterFactory.Config> {


    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            log.info("[{}] date send", config.service);
            System.out.println("Request: " + exchange.getRequest());
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("[{}] date receive. result : {}", exchange.getResponse().getStatusCode());
            }));

        };
    }


    public static class Config {

        public Config() {

        }

        String service;

        @Builder(toBuilder = false)
        public Config(String service) {
            this.service = service;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        //Put the configuration properties for your filter here
    }


    public CustomLoggingGatewayFilterFactory() {
        super(Config.class);
    }




}
