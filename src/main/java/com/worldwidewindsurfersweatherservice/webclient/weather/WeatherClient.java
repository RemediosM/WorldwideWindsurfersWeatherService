package com.worldwidewindsurfersweatherservice.webclient.weather;

import com.worldwidewindsurfersweatherservice.model.WeatherDto;
import com.worldwidewindsurfersweatherservice.webclient.weather.dto.WeatherbitDataDto;
import com.worldwidewindsurfersweatherservice.webclient.weather.dto.WeatherbitDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Component
public class WeatherClient {

    private static final String WEATHER_URL = "https://api.weatherbit.io/v2.0/forecast/";
    private static final String API_KEY = "";
    private RestTemplate restTemplate = new RestTemplate();

    public WeatherDto getWeatherForCity(String city, String date) {
        WeatherbitDto weatherbitDto = callGetMethod("daily?city={city}&key={apiKey}",
                WeatherbitDto.class,
                city,
                API_KEY);
        WeatherbitDataDto weatherDataForDate = getDataForDate(weatherbitDto, date);
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

    private WeatherbitDataDto getDataForDate(WeatherbitDto weatherbitDto, String date){
        return weatherbitDto.getData().stream()
                .filter(d -> d.getDatetime().equals(date))
                .findFirst()
                .orElse(null);

    }
    public WeatherDto getWeatherForDate(String date){

        List<WeatherDto> citiesList = new ArrayList<>();
        citiesList.add(getWeatherForCity("Jastarnia", date));
        citiesList.add(getWeatherForCity("Bridgetown", date));
        citiesList.add(getWeatherForCity("Fortaleza", date));
        citiesList.add(getWeatherForCity("Pissouri", date));
        citiesList.add(getWeatherForCity("Petite Case Noyale", date));


        return   citiesList.stream()
                .filter(c -> c.getTemp()  >= 5 && c.getTemp() <= 35
                        && c.getWindSpeed() >=5 && c.getWindSpeed() <= 18)
                .max(Comparator.comparing(p ->  p.getWindSpeed() * 3 + p.getTemp() ))
                .orElse(null);
    }

    private <T> T callGetMethod(String url, Class<T> responseType, Object... objects) {
        return restTemplate.getForObject(WEATHER_URL + url,
                responseType,objects);
    }


}
