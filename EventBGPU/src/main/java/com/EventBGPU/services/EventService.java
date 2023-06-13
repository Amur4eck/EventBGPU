package com.EventBGPU.services;

import com.EventBGPU.models.Event;
import com.EventBGPU.models.User;
import com.EventBGPU.repositories.EventRepository;
import com.EventBGPU.repositories.ImageRepository;
import com.EventBGPU.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    public EventService(EventRepository eventRepository, ImageRepository imageRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
    }

    public List<Event> listEvent(){
        return eventRepository.findAllByOrderByDateAscTimeAsc();
    }

    public List<Event> listEarliestEvents(){
        return eventRepository.findTop3AllByOrderByDateAscTimeAsc();
    }

    public void saveEvent (Event event, Principal principal) {
        User user;
        if(principal == null){
            user = new User();
        }
        else {
            user = userRepository.findByEmail(principal.getName());
        }
        event.setUser(user);
        eventRepository.save(event);
    }

    public void saveEvent (Event event, Event oldEvent) {
        event.setUser(oldEvent.getUser());
        event.setDateOfCreation(oldEvent.getDateOfCreation());
        eventRepository.save(event);
    }

    public void saveEvent (Event event) {
        eventRepository.save(event);
    }

    public void deleteEventById (Long id){
        eventRepository.deleteById(id);
    }

    public Event findEventById (Long id){
        return eventRepository.findById(id).orElse(null);
    }
}
