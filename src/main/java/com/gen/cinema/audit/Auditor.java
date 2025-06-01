package com.gen.cinema.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class Auditor implements AuditorAware<String> {

    private static final Logger logger = LoggerFactory.getLogger(Auditor.class);

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        try {
            var context = SecurityContextHolder.getContext();
            var auth = context.getAuthentication();
            logger.debug("Auditor called - Context: {}, Auth: {}", context, auth);
            
            if (auth == null) {
                logger.warn("No authentication found in security context");
                return Optional.of("SYSTEM");
            }
            
            var principal = auth.getPrincipal();
            logger.debug("Principal type: {}", principal.getClass().getName());
            
            if (principal instanceof Principal) {
                Principal userPrincipal = (Principal) principal;
                logger.debug("Found Principal - name: {}, userId: {}", userPrincipal.getName(), userPrincipal.getUserId());
                return Optional.of(userPrincipal.getUserId());
            } else {
                logger.warn("Principal is not of type Principal: {}", principal);
                return Optional.of("SYSTEM");
            }
        } catch (Exception e) {
            logger.error("Error getting current auditor", e);
            return Optional.of("SYSTEM");
        }
    }

}
