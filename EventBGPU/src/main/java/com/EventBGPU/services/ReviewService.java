package com.EventBGPU.services;

import com.EventBGPU.models.Event;
import com.EventBGPU.models.Review;
import com.EventBGPU.models.User;
import com.EventBGPU.repositories.ReviewRepository;
import com.EventBGPU.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    public List<Review> listReview(Event event){
        return reviewRepository.findByEventOrderByDateOfCreationDesc(event);
    }

    public void createReview (Review review, Event event, Principal principal) {
        User user;
        if(principal == null){
            user = new User();
        }
        else {
            user = userRepository.findByEmail(principal.getName());
        }
        review.setUser(user);
        review.setEvent(event);
        reviewRepository.save(review);
    }

    public Review findReviewById (Long id){
        return reviewRepository.findById(id).orElse(null);
    }

}
