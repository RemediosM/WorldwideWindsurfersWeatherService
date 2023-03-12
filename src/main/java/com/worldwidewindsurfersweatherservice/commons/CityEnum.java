package com.worldwidewindsurfersweatherservice.commons;

public enum CityEnum {

    JASTARNIA("Jastarnia"),
    BRIDGETOWN("Bridgetown"),
    FORTALEZA("Fortaleza"),
    PISSOURI("Pissouri"),
    PETITE_CASA_NOYALE("Petite Case Noyale");
    private final String city;

    CityEnum(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }
}
