package org.reda.billingservice.servicesFeignClient;

import org.reda.billingservice.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient
public interface ProductRestClient {

    @GetMapping(path = "/products/{id}")
    public Product findProductById(@PathVariable Long id);
}
