package com.example.s13firstspring.models.dtos;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
public class CityWithoutDeliveriesDTO implements Serializable {
    private long id;
    private String city_name;
}
