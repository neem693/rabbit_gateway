package com.demo.gateway;

import com.demo.gateway.filter.CustomLoggingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}




	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder rlb, CustomLoggingFilter
			customLoggingFilter) {

		CustomLoggingFilter.Config customLoggingGatewayFilterFactoryConfig =
				CustomLoggingFilter.Config.builder()
						.service("service-b")
						.build();


		return rlb
				.routes()
				.route(p -> p
						.path("/microservice-b/**")
						.filters(f -> f
								.rewritePath("/microservice-b/(?<segment>.*)", "/$\\{segment}")
								.filter(customLoggingFilter.apply(customLoggingGatewayFilterFactoryConfig)))

						.uri("lb://microservice-b")
				)
				.build();
	}

}
