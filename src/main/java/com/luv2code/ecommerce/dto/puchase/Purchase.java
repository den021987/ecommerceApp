package com.luv2code.ecommerce.dto.puchase;

import com.luv2code.ecommerce.entity.Address;
import com.luv2code.ecommerce.entity.Customer;
import com.luv2code.ecommerce.entity.Order;
import com.luv2code.ecommerce.entity.OrderItem;
import lombok.Data;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems = new HashSet<>();  // Initialize here
    private boolean sameAsShipping;
}