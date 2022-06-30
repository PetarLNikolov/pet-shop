package com.example.s13firstspring.modelsTests.dtos.deliveries;

import lombok.Data;

@Data
public class DeliveryEditDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String region;
    private int postCode;
    private int cityId;
    private int houseNumber;
    private int floor;
    private int apartmentNumber;
    Boolean needsBill;
    private String extraInformation;

}
