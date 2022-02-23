package com.example.s13firstspring.models.dtos;

import com.example.s13firstspring.models.entities.City;
import com.example.s13firstspring.models.entities.Order;
import lombok.Data;

import java.util.List;

@Data
public class DeliveryResponseDTO {
    String firstName;
    String lastName;
    String phoneNumber;
    String region;
    int postCode;
    CityWithoutDeliveriesDTO city;
    int houseNumber;
    int floor;
    int apartmentNumber;
    Boolean needsBill;
    String extraInformation;
    List<OrderResponseDTO> orders;
}
