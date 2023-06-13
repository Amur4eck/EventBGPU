package com.EventBGPU.repositories;

import com.EventBGPU.models.Event;
import com.EventBGPU.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByEventOrderByDateOfCreationDesc(Event event);

}
