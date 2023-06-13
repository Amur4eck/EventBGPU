package com.EventBGPU.services;

import com.EventBGPU.controllers.UserController;
import com.EventBGPU.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //if(userRepository.findByEmail(email) == null){
        //    throw new UsernameNotFoundException("User not authorized.");
        //}
        log.info("Try to login");
        return userRepository.findByEmail(email);
    }

}
