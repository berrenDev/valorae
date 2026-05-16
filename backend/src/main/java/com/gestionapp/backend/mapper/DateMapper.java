package com.gestionapp.backend.mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

@Component
public class DateMapper {

    public OffsetDateTime map(LocalDateTime localDateTime) {

        if (localDateTime == null) {
            return null;
        }

        return localDateTime.atOffset(ZoneOffset.UTC);
    }

    public LocalDateTime map(OffsetDateTime offsetDateTime) {

        if (offsetDateTime == null) {
            return null;
        }

        return offsetDateTime.toLocalDateTime();
    }

}