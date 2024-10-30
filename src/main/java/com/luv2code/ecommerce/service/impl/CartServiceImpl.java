package com.luv2code.ecommerce.service.impl;

import com.luv2code.ecommerce.entity.CartItem;
import com.luv2code.ecommerce.entity.Product;
import com.luv2code.ecommerce.repository.CartItemRepository;
import com.luv2code.ecommerce.repository.ProductRepository;
import com.luv2code.ecommerce.service.CartService;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Data
@Service
public class CartServiceImpl implements CartService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;


    @Override
    public void addToCart(Product product, int quantity) {
        // Check if product already in cart
        CartItem existingCartItem = cartItemRepository.findAll().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        if (existingCartItem != null) {
            // If product already in cart, increase the quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            cartItemRepository.save(existingCartItem);
        } else {
            // Else, create a new cart item
            CartItem cartItem = new CartItem(product, quantity);
            cartItemRepository.save(cartItem);
        }
    }

    @Override
    public void removeFromCart(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public void incrementProduct(Long productId) {
        List<CartItem> cartItems = cartItemRepository.findAll(); // Retrieve all cart items from the repository

        for (CartItem cartItem : cartItems) { // Iterate over each cart item
            if (cartItem.getProduct().getId().equals(productId)) { // Check if the product ID matches the given product ID
                cartItem.setQuantity(cartItem.getQuantity() + 1); // Increment the quantity by 1
                cartItemRepository.save(cartItem); // Save the updated cart item back to the repository
                break; // Exit the loop once the matching cart item is found and updated
            }
        }
    }

    @Override
    public void decrementProduct(Long productId) {
        CartItem cartItem = cartItemRepository.findAll().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (cartItem != null && cartItem.getQuantity() > 1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            cartItemRepository.save(cartItem);
        } else if (cartItem != null) {
            cartItemRepository.delete(cartItem);
        }
    }

    @Override
    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    @Override
    public double getTotalPrice() {
        return cartItemRepository.findAll().stream()
                .mapToDouble(cartItem -> cartItem.getProduct().getUnitPrice().doubleValue() * cartItem.getQuantity())
                .sum();
    }

    @Override
    public int getTotalQuantity() {
        return cartItemRepository.findAll().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
}

