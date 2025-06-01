package com.gen.cinema.config;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeZoneAwareInstantSerializer extends JsonSerializer<Instant> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");

    @Override
    public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        
        ZoneId zoneId = TimeZoneFilter.getCurrentTimeZone();
        String formattedTime = value.atZone(zoneId).format(formatter);
        gen.writeString(formattedTime);
    }
} 