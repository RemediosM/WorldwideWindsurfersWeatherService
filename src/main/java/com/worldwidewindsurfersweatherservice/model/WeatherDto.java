package com.worldwidewindsurfersweatherservice.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeatherDto {

    private String cityName;
    private String countryCode;
    private float lat;
    private float lon;
    private String stateCode;
    private String timezone;
    private String datetime;
    private float temp;
    private float windSpeed;

}
