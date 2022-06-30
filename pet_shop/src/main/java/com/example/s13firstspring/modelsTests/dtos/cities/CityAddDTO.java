package com.example.s13firstspring.modelsTests.dtos.cities;

import lombok.Data;

import javax.persistence.Column;

@Data
public class CityAddDTO {

    @Column
    private String city_name;

}
