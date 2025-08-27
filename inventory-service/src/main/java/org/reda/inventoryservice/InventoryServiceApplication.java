package org.reda.inventoryservice;

import lombok.Builder;
import org.reda.inventoryservice.entities.Product;
import org.reda.inventoryservice.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.List;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository, RepositoryRestConfiguration restConfiguration){
        return args -> {
            restConfiguration.exposeIdsFor(Product.class);
            productRepository.saveAll(List.of(
                    Product.builder().name("Computer").price(3600).quantity(40).build(),
                    Product.builder().name("Air-Fryer").price(900).quantity(13).build(),
                    Product.builder().name("Smartphone").price(1200).quantity(97).build(),
                    Product.builder().name("Ipad").price(6200).quantity(55).build()
            ));
        };
    }

}
