package com.EventBGPU.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name, description, location;
    private LocalDate date;
    private LocalTime time;
    private LocalDateTime dateOfCreation;
    @OneToOne(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Image image;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    public Event() {
    }

    public Event(String name, String description, String location, LocalDate date, LocalTime time) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.date = date;
        this.time = time;
    }

    @PrePersist
    private void init(){
        dateOfCreation = LocalDateTime.now();
    }

    public void setImage(Image image){
        image.setEvent(this);
        this.image = image;
    }
    public Image getImage(){
        if (image != null){
            return image;
        }
        else{
            return new Image("Placeholder.png");
        }
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }
}
