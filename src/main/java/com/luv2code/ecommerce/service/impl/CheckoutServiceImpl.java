package com.luv2code.ecommerce.service.impl;

import com.luv2code.ecommerce.repository.CountryRepository;
import com.luv2code.ecommerce.repository.CustomerRepository;
import com.luv2code.ecommerce.dto.puchase.Purchase;
import com.luv2code.ecommerce.dto.puchase.PurchaseResponse;
import com.luv2code.ecommerce.entity.Customer;
import com.luv2code.ecommerce.entity.Order;
import com.luv2code.ecommerce.entity.OrderItem;
import com.luv2code.ecommerce.service.CheckoutService;
import com.luv2code.ecommerce.service.CountryService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
@Data
public class CheckoutServiceImpl implements CheckoutService {

    private CustomerRepository customerRepository;

    private CountryService countryService;

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        Order order = purchase.getOrder();
        if (order == null) {
            order = new Order();
            purchase.setOrder(order);
        }

        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // Check if orderItems is null before iterating
        Set<OrderItem> orderItems = purchase.getOrderItems();
        if (orderItems == null) {
            orderItems = new HashSet<>();  // Initialize it to an empty set if null
            purchase.setOrderItems(orderItems); // Save it back to the purchase
        }
        orderItems.forEach(order::add);

        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        Customer customer = purchase.getCustomer();
        customer.add(order);

        customerRepository.save(customer);

        return new PurchaseResponse(orderTrackingNumber);
    }


    public String generateOrderTrackingNumber() {

        // generate a random UUID number (UUID version-4)
        // For details see: http://en.wikipedia.org/wiki/Universaly_unique_identifier

        return UUID.randomUUID().toString();
    }
}
