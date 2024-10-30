package com.luv2code.ecommerce.service;

import com.luv2code.ecommerce.dto.puchase.Purchase;
import com.luv2code.ecommerce.dto.puchase.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);
}
