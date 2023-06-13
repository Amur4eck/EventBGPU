package com.EventBGPU.controllers;

import com.EventBGPU.models.User;
import com.EventBGPU.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class UserController {

    private Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login (Authentication authentication){
        if (authentication != null){
            return "redirect:/";
        }
        else {
            return "login";
        }
    }
    @GetMapping("/registration")
    public String registration (Authentication authentication){
        if (authentication != null){
            return "redirect:/";
        }
        else {
            return "registration";
        }
    }
    @PostMapping("/registration")
    public String createUser (User user){
        if (!userService.alreadyNotExists(user)) {
            return "registration";
        }
        userService.createUser(user);
        return "redirect:/login";
    }
    @GetMapping("/hello")
    public String securityUrl (){
        return "hello";
    }
}
