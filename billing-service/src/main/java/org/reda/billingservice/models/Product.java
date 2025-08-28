package org.reda.billingservice.models;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private int price;
    private int quantity;
}
