package com.luv2code.ecommerce;

import com.luv2code.ecommerce.entity.CartItem;
import com.luv2code.ecommerce.entity.Product;
import com.luv2code.ecommerce.repository.CartItemRepository;
import com.luv2code.ecommerce.service.impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CartServiceImplTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private CartServiceImpl cartService; // Assuming this is the class with the incrementProduct method

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test
    }

    @Test
    void testIncrementProduct_ProductExists() throws Exception {
        // Arrange
        Long productId = 1L;

        // Create Product objects
        Product product1 = new Product();
        Product product2 = new Product();

        // Set the product IDs using reflection
        setProductId(product1, productId);
        setProductId(product2, 2L); // Different product ID

        // Create CartItems with the associated products
        CartItem cartItem1 = new CartItem();
        cartItem1.setProduct(product1);
        cartItem1.setQuantity(5);

        CartItem cartItem2 = new CartItem();
        cartItem2.setProduct(product2);

        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem1);
        cartItems.add(cartItem2);

        when(cartItemRepository.findAll()).thenReturn(cartItems);

        // Act
        cartService.incrementProduct(productId);

        // Assert
        assertEquals(6, cartItem1.getQuantity()); // Check if the quantity was incremented
        verify(cartItemRepository, times(1)).save(cartItem1); // Verify save was called once on cartItem1
    }

    // Helper method to set the product ID via reflection
    private void setProductId(Product product, Long id) throws Exception {
        Field idField = Product.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(product, id);
    }
}
