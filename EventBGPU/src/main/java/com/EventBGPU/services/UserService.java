package com.EventBGPU.services;

import com.EventBGPU.models.enums.Role;
import com.EventBGPU.models.User;
import com.EventBGPU.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser (User user){
        log.info("User with email " + user.getUsername() + " is CREATED!");
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRole().add(Role.ROLE_USER);
        userRepository.save(user);
    }

    public User findUserByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    public boolean alreadyNotExists (User user){
        if (userRepository.findByEmail(user.getUsername()) != null) return false;
        else return true;
    }
}
