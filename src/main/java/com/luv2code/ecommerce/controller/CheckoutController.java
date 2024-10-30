package com.luv2code.ecommerce.controller;

import com.luv2code.ecommerce.dto.puchase.Purchase;
import com.luv2code.ecommerce.dto.puchase.PurchaseResponse;
import com.luv2code.ecommerce.entity.Address;
import com.luv2code.ecommerce.entity.Country;
import com.luv2code.ecommerce.entity.State;
import com.luv2code.ecommerce.service.CheckoutService;
import com.luv2code.ecommerce.service.CountryService;
import com.luv2code.ecommerce.service.StateService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;
    private final CountryService countryService;
    private final StateService stateService;
    @GetMapping("/checkout")
    public String showCheckoutForm(@RequestParam(value = "selectedCountry", required = false) String countryCode,
                                   @ModelAttribute("checkoutForm") Purchase checkoutForm,
                                   Model model) {

        // Initialize addresses if they are null
        if (checkoutForm.getShippingAddress() == null) {
            checkoutForm.setShippingAddress(new Address());
        }
        if (checkoutForm.getBillingAddress() == null) {
            checkoutForm.setBillingAddress(new Address());
        }

        // Fetch the list of countries and add it to the model
        model.addAttribute("countries", countryService.getAllCountries());

        // Fetch the states based on the provided country code, if any
        List<State> states = new ArrayList<>();
        if (countryCode != null && !countryCode.isEmpty()) {
            states = stateService.findStatesByCountryCode(countryCode);
            checkoutForm.getShippingAddress().setCountry(new Country(countryCode)); // Preserve selected country
        }

        // Add the states and checkout form to the model
        model.addAttribute("states", states);
        model.addAttribute("checkoutForm", checkoutForm);

        return "checkout/checkout";  // Thymeleaf template name
    }
    @PostMapping("/checkout")
    public String processCheckout(@ModelAttribute("checkoutForm") Purchase checkoutForm,
                                  Model model,
                                  HttpSession session) {

        String shippingCountryCode = checkoutForm.getShippingAddress().getCountry().getCode();
        if (shippingCountryCode != null) {
            Country shippingCountry = countryService.findByCode(shippingCountryCode);
            checkoutForm.getShippingAddress().setCountry(shippingCountry);
        }

        String billingCountryCode = checkoutForm.getBillingAddress().getCountry().getCode();
        if (billingCountryCode != null) {
            Country billingCountry = countryService.findByCode(billingCountryCode);
            checkoutForm.getBillingAddress().setCountry(billingCountry);
        }

        // Call the service to place the order
        PurchaseResponse response = checkoutService.placeOrder(checkoutForm);

        // Prepare model attributes for success page
        model.addAttribute("trackingNumber", response.getOrderTrackingNumber());
        session.removeAttribute("cart");


        return "checkout/checkout-success";  // Thymeleaf template for success page
    }
//    @GetMapping("/checkout")
//    public String showCheckoutForm(@RequestParam(value = "selectedCountry", required = false) String countryCode,
//                                   @ModelAttribute("checkoutForm") Purchase checkoutForm,
//                                   Model model) {
//
//        // Initialize addresses if they are null
//        if (checkoutForm.getShippingAddress() == null) {
//            checkoutForm.setShippingAddress(new Address());
//        }
//        if (checkoutForm.getBillingAddress() == null) {
//            checkoutForm.setBillingAddress(new Address());
//        }
//
//        // Fetch the list of countries and add it to the model
//        model.addAttribute("countries", countryService.getAllCountries());
//
//        // Fetch the states based on the provided country code, if any
//        List<State> states = new ArrayList<>();
//        if (countryCode != null && !countryCode.isEmpty()) {
//            states = stateService.findStatesByCountryCode(countryCode);
//            checkoutForm.getShippingAddress().setCountry(countryCode); // Preserve the selected country
//        }
//
//        // Add the states and checkout form to the model
//        model.addAttribute("states", states);
//        model.addAttribute("checkoutForm", checkoutForm);
//
//        return "checkout/checkout";  // Thymeleaf template name
//    }
//    @PostMapping("/checkout")
//    public String showCheckoutForm(@ModelAttribute("checkoutForm") Purchase checkoutForm,
//                                   Model model,
//                                   HttpSession session) {
//        PurchaseResponse response = checkoutService.placeOrder(checkoutForm);
//
//        // Fetch the list of countries and add it to the model
//        model.addAttribute("countries", countryService.getAllCountries());
//        model.addAttribute("trackingNumber", response.getOrderTrackingNumber());
//        // Fetch the states for the shipping address
//        List<State> shippingStates = new ArrayList<>();
//        String shippingCountryCode = checkoutForm.getShippingAddress().getCountry();
//        if (shippingCountryCode != null && !shippingCountryCode.isEmpty()) {
//            shippingStates = stateService.findStatesByCountryCode(shippingCountryCode);
//        }
//
//        // Fetch the states for the billing address
//        List<State> billingStates = new ArrayList<>();
//        String billingCountryCode = checkoutForm.getBillingAddress().getCountry();
//        if (billingCountryCode != null && !billingCountryCode.isEmpty()) {
//            billingStates = stateService.findStatesByCountryCode(billingCountryCode);
//        }
//
//        session.removeAttribute("cart");
//        model.addAttribute("m", 1); // or set it to any integer value
//
//        // Add the states and checkout form to the model
//        model.addAttribute("shippingStates", shippingStates);
//        model.addAttribute("billingStates", billingStates);
//        model.addAttribute("checkoutForm", checkoutForm);
//
//        return "checkout/checkout-success";  // Thymeleaf template name
//    }

}