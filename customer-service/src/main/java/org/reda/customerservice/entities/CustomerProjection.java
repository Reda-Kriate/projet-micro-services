package org.reda.customerservice.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "miniCustomer",types = Customer.class)
public interface CustomerProjection {
    public Long getId();
    public String getEmail();
}
