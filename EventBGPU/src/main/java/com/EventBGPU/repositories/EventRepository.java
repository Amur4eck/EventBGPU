package com.EventBGPU.repositories;

import com.EventBGPU.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByName(String name);

    List<Event> findAllByOrderByDateAscTimeAsc();

    List<Event> findTop3AllByOrderByDateAscTimeAsc();
}
