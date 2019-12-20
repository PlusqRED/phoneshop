package com.es.phoneshop.web.controller;

import com.es.core.model.user.User;
import com.es.core.service.user.SecurityService;
import com.es.core.service.user.UserService;
import com.es.phoneshop.web.validators.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;

@Controller
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private SecurityService securityService;

    @Resource
    private UserValidator userValidator;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(userValidator);
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@Validated @ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.save(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getPassword());
        return "redirect:/productList";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }
        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }
        return "login";
    }
}
