package com.worldwidewindsurfersweatherservice.webclient.weather.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class WeatherbitDto {

    private String city_name;
    private String country_code;
    private float lat;
    private float lon;
    private String state_code;
    private String timezone;
    private List<WeatherbitDataDto> data;

}
