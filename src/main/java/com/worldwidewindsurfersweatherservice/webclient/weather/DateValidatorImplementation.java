package com.worldwidewindsurfersweatherservice.webclient.weather;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component
public class DateValidatorImplementation implements DateValidator {

    private static final long NUMBER_OF_DAYS_FORECAST = 16;

    @Override
    public boolean isValid(String dateStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr);
            LocalDate today = LocalDate.now(Clock.systemDefaultZone());
            return (date.isAfter(today) || date.isEqual(today)) && date.isBefore(today.plusDays(NUMBER_OF_DAYS_FORECAST));
        } catch (DateTimeParseException e) {
            return false;
        }
    }

}
