package org.reda.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    //remplace les config dans application.yml -> les deux sont static (avant la creation de discovery service)
    //dependencie qu'on doit utilise ** spring-cloud-starter-gateway-server-webflux
    //@Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/customers/**").uri("http://localhost:8081/")) //adresse
                .route(r -> r.path("/products/**").uri("lb://INVENTORY-SERVICE")) //load-balancer avec le nom
                                                                                                // de micro service
                .build();
    }
    //Configuration dynamique sans @Ip et Nom de service
    //on doit mapper sur http://localhost:8888/NOM-SERVICE/endpoint = http://localhost:8888/CUSTOMER-SERVICE/customers
    @Bean
    public DiscoveryClientRouteDefinitionLocator definitionLocator(ReactiveDiscoveryClient rdc,
                                                                   DiscoveryLocatorProperties dlp){
        return new DiscoveryClientRouteDefinitionLocator(rdc,dlp);
    }
}
