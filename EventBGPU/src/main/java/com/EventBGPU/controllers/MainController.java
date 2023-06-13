package com.EventBGPU.controllers;

import com.EventBGPU.models.User;
import com.EventBGPU.services.EventService;
import com.EventBGPU.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Controller
public class MainController {

    private final EventService eventService;
    private final UserService userService;

    @Autowired
    public MainController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        String nameFamily;
        if(authentication != null) {
            User user = userService.findUserByEmail(authentication.getName());
            nameFamily = user.getName() + " " + user.getFamily();
        }
        else  {
            nameFamily = "";
        }
        model.addAttribute("title", "Main page");
        model.addAttribute("topEvents", eventService.listEarliestEvents());
        model.addAttribute("events", eventService.listEvent());
        model.addAttribute("name", nameFamily);

        return "home";
    }


}
