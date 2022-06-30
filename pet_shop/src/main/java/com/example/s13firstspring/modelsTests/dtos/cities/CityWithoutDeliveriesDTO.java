package com.example.s13firstspring.modelsTests.dtos.cities;

import lombok.Data;

import java.io.Serializable;

@Data
public class CityWithoutDeliveriesDTO implements Serializable {
    private int id;
    private String city_name;
}
