package com.example.s13firstspring.models.dtos.deliveries;

import com.example.s13firstspring.models.dtos.cities.CityWithoutDeliveriesDTO;
import lombok.Data;

import java.io.Serializable;

@Data
public class DeliveryWithCityDTO implements Serializable {
    private int id;
    private String firstName1;
    private String lastName1;
    private String phoneNumber1;
    private String region;
    private Integer postCode1;
    private CityWithoutDeliveriesDTO cities;
    private Integer houseNumber;
    private Integer floor;
    private Integer appartamentNumber;
    private Boolean needsBill;
    private String extraInfromation;
}
