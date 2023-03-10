package com.worldwidewindsurfersweatherservice.controller;

import com.worldwidewindsurfersweatherservice.model.WeatherDto;
import com.worldwidewindsurfersweatherservice.service.WeatherService;
import com.worldwidewindsurfersweatherservice.webclient.weather.DateValidatorImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;
    private final DateValidatorImplementation dateValidatorImplementation;

    @GetMapping("/weather")
    public WeatherDto getWeather() {
        return weatherService.getWeather();
    }

    @GetMapping("/weather/{date}")
    public WeatherDto getWeatherForDate(@PathVariable String date) {
        return dateValidatorImplementation.isValid(date) ? weatherService.getWeatherForDate(date) : null;
    }
}
