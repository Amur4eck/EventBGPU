package com.EventBGPU.controllers;

import com.EventBGPU.models.Event;
import com.EventBGPU.models.User;
import com.EventBGPU.services.EventService;
import com.EventBGPU.services.ImageService;
import com.EventBGPU.services.ReviewService;
import com.EventBGPU.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
public class EventController {

    private final EventService eventService;
    private final ImageService imageService;
    private final ReviewService reviewService;
    private final UserService userService;

    public EventController(EventService eventService, ImageService imageService, ReviewService reviewService, UserService userService) {
        this.eventService = eventService;
        this.imageService = imageService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    private String getUserNameFamily(Authentication authentication){
        if(authentication != null) {
            User user = userService.findUserByEmail(authentication.getName());
            return user.getName() + " " + user.getFamily();
        }
        else  {
            return "";
        }
    }

    @Value("${images.path}")
    private String path;

    @GetMapping("/event/create")
    public String eventCreate(Model model, Authentication authentication) {
        model.addAttribute("name", getUserNameFamily(authentication));
        return "event-create";
    }

    @PostMapping("/event/create")
    public String eventCreatePost(@RequestParam("file") MultipartFile file, Event event, Principal principal) throws IOException {
        eventService.saveEvent(event, principal);
        imageService.saveImage(file, event);

        return "redirect:/";
    }

    @GetMapping("/event/{id}")
    public String event(@PathVariable Long id, Model model, Principal principal, Authentication authentication) {
        Event event = eventService.findEventById(id);
        User author = event.getUser();
        boolean isAuthor = false;
        if(principal.getName().equals(author.getUsername())){
            isAuthor = true;
        }
        model.addAttribute("event", event);
        model.addAttribute("image", event.getImage());
        model.addAttribute("reviews", reviewService.listReview(event));
        model.addAttribute("author", author.getName() + " " + author.getFamily());
        model.addAttribute("isAuthor", isAuthor);
        model.addAttribute("name", getUserNameFamily(authentication));
        return "event";
    }

    @PostMapping("/event/{id}/delete")
    public String eventDelete(@PathVariable Long id) {
        eventService.deleteEventById(id);
        return "redirect:/";
    }

    @GetMapping("/event/{id}/edit")
    public String eventEdit(@PathVariable Long id, Model model) {
        model.addAttribute("event", eventService.findEventById(id));
        return "event-edit";
    }

    @PostMapping("/event/{id}/edit")
    public String eventEditPost(@PathVariable Long id, Event event) {
        Event oldEvent = eventService.findEventById(id);
        eventService.saveEvent(event, oldEvent);
        return "redirect:/event/{id}";
    }
}
