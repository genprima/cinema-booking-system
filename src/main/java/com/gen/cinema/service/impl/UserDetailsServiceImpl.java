package com.gen.cinema.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.gen.cinema.audit.Principal;
import com.gen.cinema.domain.User;
import com.gen.cinema.repository.UserRepository;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        
        Principal principal = new Principal(username, List.of(new SimpleGrantedAuthority(user.getRole().name())));
        principal.setUserId(user.getSecureId().toString());
        principal.setPassword(user.getPassword());
        return principal;
    }
} 