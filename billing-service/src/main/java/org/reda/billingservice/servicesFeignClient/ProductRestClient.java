package org.reda.billingservice.servicesFeignClient;

import org.reda.billingservice.entities.ProductItem;
import org.reda.billingservice.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.web.config.SpringDataJacksonConfiguration;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

@FeignClient(name = "INVENTORY-SERVICE")
public interface ProductRestClient {

    @GetMapping(path = "/products/{id}")
     Product findProductById(@PathVariable Long id);

    @GetMapping(path = "/products")
    PagedModel<Product> allProducts();
}
