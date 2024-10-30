package com.luv2code.ecommerce.controller;

import com.luv2code.ecommerce.dto.user.UserDto;
import com.luv2code.ecommerce.entity.User;
import com.luv2code.ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class AuthController {

    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "security/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "security/register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult bindingResult,
                               Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());
        if (existingUser != null) {
            bindingResult.rejectValue("email", null, "email already exists");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDto);
            return "security/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    //Handler method to handle users
    @GetMapping("users")
    public String showUsers(Model model) {

        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);

        return "security/users";
    }
}