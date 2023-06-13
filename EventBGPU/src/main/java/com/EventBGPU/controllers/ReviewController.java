package com.EventBGPU.controllers;

import com.EventBGPU.models.Review;
import com.EventBGPU.services.EventService;
import com.EventBGPU.services.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.security.Principal;

@Controller
public class ReviewController {

    private final EventService eventService;
    private final ReviewService reviewService;

    public ReviewController(EventService eventService, ReviewService reviewService) {
        this.eventService = eventService;
        this.reviewService = reviewService;
    }

    @PostMapping("/event/{id}/review")
    public String eventCreatePost(@PathVariable Long id, Review review, Principal principal) throws IOException {
        reviewService.createReview(review, eventService.findEventById(id), principal);
        return "redirect:/event/{id}";
    }
}
