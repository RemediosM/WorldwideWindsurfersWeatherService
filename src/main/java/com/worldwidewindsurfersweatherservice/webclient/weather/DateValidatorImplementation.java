package com.worldwidewindsurfersweatherservice.webclient.weather;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component
public class DateValidatorImplementation implements DateValidator {
    @Override
    public boolean isValid(String dateStr) {

        boolean isValid;
        try {
            LocalDate date = LocalDate.parse(dateStr);
            LocalDate today = LocalDate.now();
            isValid = (date.isAfter(today) || date.isEqual(today))
                    && date.isBefore(today.plusDays(16));

        } catch (DateTimeParseException e) {
            return false;
        }
        return isValid;
    }
}
