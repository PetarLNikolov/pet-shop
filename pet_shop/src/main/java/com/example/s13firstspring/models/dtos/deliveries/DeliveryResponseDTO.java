package com.example.s13firstspring.models.dtos.deliveries;

import com.example.s13firstspring.models.dtos.orders.OrderResponseDTO;
import com.example.s13firstspring.models.dtos.cities.CityWithoutDeliveriesDTO;
import lombok.Data;

import java.util.List;

@Data
public class DeliveryResponseDTO {
    int id;
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
