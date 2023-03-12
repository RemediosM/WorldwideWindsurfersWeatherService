package com.worldwidewindsurfersweatherservice.webclient.weather.dto;

import lombok.Getter;

@Getter
public class WeatherbitDataDto {

    private String datetime;
    private Float temp;
    private Float wind_spd;

}
