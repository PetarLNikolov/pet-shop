package com.example.s13firstspring.models.dtos;

import lombok.Data;

import java.util.List;

@Data
public class DeliveryEditDTO {
    String firstName;
    String lastName;
    String phoneNumber;
    String region;
    int postCode;
    int cityId;
    int houseNumber;
    int floor;
    int apartmentNumber;
    Boolean needsBill;
    String extraInformation;
    List<Integer> ordersIds;
}
