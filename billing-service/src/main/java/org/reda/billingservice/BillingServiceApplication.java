package org.reda.billingservice;

import org.reda.billingservice.entities.Bill;
import org.reda.billingservice.entities.ProductItem;
import org.reda.billingservice.models.Customer;
import org.reda.billingservice.models.Product;
import org.reda.billingservice.repositories.BillRepository;
import org.reda.billingservice.repositories.ProductItemRepository;
import org.reda.billingservice.servicesFeignClient.CustomerRestClient;
import org.reda.billingservice.servicesFeignClient.ProductRestClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(BillRepository billRepository,
                            ProductItemRepository productItemRepository,
                            CustomerRestClient customerRestClient,
                            ProductRestClient productRestClient,
                            RepositoryRestConfiguration restConfiguration){
        return args -> {
            restConfiguration.exposeIdsFor(Bill.class,ProductItem.class);
            Collection<Product> products = productRestClient.allProducts().getContent();
            Long customerId = 1L;
            Customer customer = customerRestClient.findCustomerById(customerId);
            if(customer == null) throw new RuntimeException(String.format("Customer %s not found",customerId));
            Bill bill = new Bill();
            bill.setCustomerId(customerId);
            bill.setBillDate(new Date());
            Bill savedBill = billRepository.save(bill);

            products.forEach(p ->{
                ProductItem productItem = new ProductItem();
                productItem.setProductId(p.getId());
                productItem.setQuantity(new Random().nextInt(10) + 1);
                productItem.setPrice(p.getPrice());
                productItem.setDiscount(Math.random());
                productItem.setBill(savedBill);

                productItemRepository.save(productItem);
            });

        };

    }

}
