package com.example.s13firstspring.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeliveryWithoutCityDTO {
    private int id;
    private String firstName1;
    private String lastName1;
    private String phoneNumber1;
    private String region;
    private Integer postCode1;
    private Integer houseNumber;
    private Integer floor;
    private Integer appartamentNumber;
    private Boolean needsBill;
    private String extraInfromation;
}
