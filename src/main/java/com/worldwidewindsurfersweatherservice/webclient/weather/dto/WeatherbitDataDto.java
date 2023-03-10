package com.worldwidewindsurfersweatherservice.webclient.weather.dto;

import lombok.Getter;

@Getter
public class WeatherbitDataDto {

    private String datetime;
    private float temp;
    private float wind_spd;

}
