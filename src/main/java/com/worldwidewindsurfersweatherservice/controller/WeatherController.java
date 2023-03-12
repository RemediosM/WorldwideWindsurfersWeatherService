package com.worldwidewindsurfersweatherservice.controller;

import com.worldwidewindsurfersweatherservice.model.WeatherDto;
import com.worldwidewindsurfersweatherservice.service.WeatherService;
import com.worldwidewindsurfersweatherservice.webclient.weather.DateValidatorImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.LocalDate;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {

    private static final String INCORRECT_DATE_ERROR = "Incorrect date. Correct date format is YYYY-MM-DD. Date should be in the range from "
            + LocalDate.now(Clock.systemDefaultZone()) + " to " + LocalDate.now(Clock.systemDefaultZone()).plusDays(15);
    private final WeatherService weatherService;
    private final DateValidatorImplementation dateValidatorImplementation;

    @GetMapping("/{date}")
    public ResponseEntity getWeatherForDate(@PathVariable String date) {
        if (dateValidatorImplementation.isValid(date)) {
            WeatherDto weatherDto = weatherService.getWeatherForDate(date);
            return ResponseEntity.ok(weatherDto);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(INCORRECT_DATE_ERROR);
        }
    }

}
