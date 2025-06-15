package com.gen.cinema.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gen.cinema.domain.User;
import com.gen.cinema.exception.BadRequestAlertException;
import com.gen.cinema.repository.UserRepository;
import com.gen.cinema.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(username)
            .orElseThrow(() -> new BadRequestAlertException("User not found"));
    }
} 