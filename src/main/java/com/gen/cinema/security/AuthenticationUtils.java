package com.gen.cinema.security;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.gen.cinema.audit.Principal;

@Component
public class AuthenticationUtils {

    public String getCurrentUserId() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Principal) {
            return ((Principal) principal).getUserId();
        }
        return null;
    }

}
