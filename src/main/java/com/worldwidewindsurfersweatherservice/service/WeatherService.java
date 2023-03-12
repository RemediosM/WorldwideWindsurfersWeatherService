package com.worldwidewindsurfersweatherservice.service;

import com.worldwidewindsurfersweatherservice.model.WeatherDto;
import com.worldwidewindsurfersweatherservice.webclient.weather.WeatherClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherClient weatherClient;

    public WeatherDto getWeatherForDate(String date) {
        return weatherClient.getWeatherForDate(date);
    }

}
