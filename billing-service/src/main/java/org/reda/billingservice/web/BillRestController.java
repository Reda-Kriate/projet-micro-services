package org.reda.billingservice.web;

import org.reda.billingservice.entities.Bill;
import org.reda.billingservice.entities.ProductItem;
import org.reda.billingservice.repositories.BillRepository;
import org.reda.billingservice.repositories.ProductItemRepository;
import org.reda.billingservice.servicesFeignClient.CustomerRestClient;
import org.reda.billingservice.servicesFeignClient.ProductRestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class BillRestController {
    private ProductItemRepository productItemRepository;
    private BillRepository billRepository;
    private CustomerRestClient customerRestClient;
    private ProductRestClient productRestClient;

    public BillRestController(ProductItemRepository productItemRepository, BillRepository billRepository, CustomerRestClient customerRestClient, ProductRestClient productRestClient) {
        this.productItemRepository = productItemRepository;
        this.billRepository = billRepository;
        this.customerRestClient = customerRestClient;
        this.productRestClient = productRestClient;
    }
    @GetMapping(path = "/fullBill/{id}")
    public Bill getBill(@PathVariable Long id){
        Bill bill = billRepository.findById(id).orElseThrow();
        bill.setCustomer(customerRestClient.findCustomerById(bill.getCustomerId()));
        List<ProductItem> productItems = bill.getProductItems();
        productItems.forEach(p->{
            p.setProduct(productRestClient.findProductById(p.getProductId()));
        });
        return bill;
    }
}
