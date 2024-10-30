package com.luv2code.ecommerce.controller;


import com.luv2code.ecommerce.entity.Product;
import com.luv2code.ecommerce.repository.ProductRepository;
import com.luv2code.ecommerce.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ProductRepository productRepository;


    @ModelAttribute
    private void addGlobalAttributes(Model model) {
        model.addAttribute("cartItemCount", cartService.getTotalQuantity());
    }
    @GetMapping
    public String showCart(Model model) {

        addGlobalAttributes(model);
        model.addAttribute("cartItems", cartService.getAllCartItems());
        model.addAttribute("totalQuantity", cartService.getTotalQuantity());
        model.addAttribute("totalPrice", cartService.getTotalPrice());
        return "cart/cart";  // return the name of the cart HTML template
    }

    @PostMapping("/addToCart")
    public String addToCart(@RequestParam Long productId,
                            @RequestParam int quantity,
                            @RequestParam int page) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id: " + productId));
        cartService.addToCart(product, quantity);
        return "redirect:/products?page=" + page;
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long cartItemId) {
        cartService.removeFromCart(cartItemId);
        return "redirect:/cart";  // Redirect to the cart page after removing an item
    }

    @PostMapping("/increment")
    public String incrementProduct(@RequestParam Long productId) {
        cartService.incrementProduct(productId);
        return "redirect:/cart";  // Redirect to the cart page after incrementing the quantity
    }

    @PostMapping("/decrement")
    public String decrementProduct(@RequestParam Long productId) {
        cartService.decrementProduct(productId);
        return "redirect:/cart";  // Redirect to the cart page after decrementing the quantity
    }

}