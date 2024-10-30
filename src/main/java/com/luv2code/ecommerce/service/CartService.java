package com.luv2code.ecommerce.service;


import com.luv2code.ecommerce.entity.CartItem;
import com.luv2code.ecommerce.entity.Product;

import java.util.List;

public interface CartService {

    void addToCart(Product product, int quantity);

    void removeFromCart(Long cartItemId);

    void incrementProduct(Long productId);

    void decrementProduct(Long productId);

    List<CartItem> getAllCartItems();

    double getTotalPrice();

    int getTotalQuantity();
}
