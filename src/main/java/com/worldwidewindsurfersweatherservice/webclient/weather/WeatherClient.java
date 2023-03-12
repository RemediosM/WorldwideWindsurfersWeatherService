package com.worldwidewindsurfersweatherservice.webclient.weather;

import com.worldwidewindsurfersweatherservice.commons.CityEnum;
import com.worldwidewindsurfersweatherservice.model.WeatherDto;
import com.worldwidewindsurfersweatherservice.webclient.weather.dto.WeatherbitDataDto;
import com.worldwidewindsurfersweatherservice.webclient.weather.dto.WeatherbitDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class WeatherClient {

    private static final String WEATHER_FOR_CITY_ENDPOINT = "daily?city={city}&key={apiKey}";
    private static final float MIN_TEMP = 5;
    private static final float MAX_TEMP = 35;
    private static final float MIN_WIND_SPEED = 5;
    private static final float MAX_WIND_SPEED = 18;
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${weather.client.url}")
    private String weatherUrl;
    @Value("${weather.client.apikey}")
    private String apiKey;

    private static WeatherDto weatherDtoBuild(WeatherbitDto weatherbitDto, WeatherbitDataDto weatherDataForDate) {
        return WeatherDto.builder()
                .cityName(weatherbitDto.getCity_name())
                .countryCode(weatherbitDto.getCountry_code())
                .stateCode(weatherbitDto.getState_code())
                .timezone(weatherbitDto.getTimezone())
                .lat(weatherbitDto.getLat())
                .lon(weatherbitDto.getLon())
                .datetime(weatherDataForDate != null ? weatherDataForDate.getDatetime() : null)
                .temp(weatherDataForDate != null ? weatherDataForDate.getTemp() : null)
                .windSpeed(weatherDataForDate != null ? weatherDataForDate.getWind_spd() : null)
                .build();
    }

    public WeatherDto getWeatherForDate(String date) {
        List<WeatherDto> citiesList = new ArrayList<>();
        citiesList.add(getWeatherForCity(CityEnum.JASTARNIA.getCity(), date));
        citiesList.add(getWeatherForCity(CityEnum.BRIDGETOWN.getCity(), date));
        citiesList.add(getWeatherForCity(CityEnum.FORTALEZA.getCity(), date));
        citiesList.add(getWeatherForCity(CityEnum.PISSOURI.getCity(), date));
        citiesList.add(getWeatherForCity(CityEnum.PETITE_CASA_NOYALE.getCity(), date));

        return citiesList.stream()
                .filter(c -> c.getTemp() >= MIN_TEMP && c.getTemp() <= MAX_TEMP
                        && c.getWindSpeed() >= MIN_WIND_SPEED && c.getWindSpeed() <= MAX_WIND_SPEED)
                .max(Comparator.comparing(p -> p.getWindSpeed() * 3 + p.getTemp()))
                .orElse(null);
    }

    private WeatherDto getWeatherForCity(String city, String date) {
        WeatherbitDto weatherbitDto = restTemplate.getForObject(weatherUrl + WEATHER_FOR_CITY_ENDPOINT, WeatherbitDto.class, city, apiKey);

        if (weatherbitDto != null) {
            WeatherbitDataDto weatherDataForDate = getWeatherDataForGivenDate(weatherbitDto, date);
            return weatherDtoBuild(weatherbitDto, weatherDataForDate);
        } else {
            return null;
        }
    }

    private WeatherbitDataDto getWeatherDataForGivenDate(WeatherbitDto weatherbitDto, @NonNull String date) {
        return weatherbitDto.getData().stream()
                .filter(d -> date.equals(d.getDatetime()))
                .findAny()
                .orElse(null);
    }

}
