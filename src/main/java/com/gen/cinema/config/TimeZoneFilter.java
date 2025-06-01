package com.gen.cinema.config;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.time.ZoneId;

@Component
public class TimeZoneFilter extends OncePerRequestFilter {
    private static final String TIMEZONE_HEADER = "X-Time-Zone";
    private static final String DEFAULT_TIMEZONE = "UTC";
    private static final ThreadLocal<ZoneId> timeZoneContext = new ThreadLocal<>();

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        
        try {
            // Get timezone from header
            String timezone = request.getHeader(TIMEZONE_HEADER);
            
            // Set timezone in context
            timeZoneContext.set(timezone != null ? 
                ZoneId.of(timezone) : 
                ZoneId.of(DEFAULT_TIMEZONE));
            
            // Continue the filter chain
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            // If invalid timezone, use default
            timeZoneContext.set(ZoneId.of(DEFAULT_TIMEZONE));
            filterChain.doFilter(request, response);
        } finally {
            // Clean up
            timeZoneContext.remove();
        }
    }

    public static ZoneId getCurrentTimeZone() {
        return timeZoneContext.get() != null ? 
            timeZoneContext.get() : 
            ZoneId.of(DEFAULT_TIMEZONE);
    }
} 