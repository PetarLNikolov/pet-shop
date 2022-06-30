package com.example.s13firstspring.modelsTests.dtos.deliveries;

import com.example.s13firstspring.modelsTests.dtos.orders.OrderResponseDTO;
import com.example.s13firstspring.modelsTests.dtos.cities.CityWithoutDeliveriesDTO;
import lombok.Data;

import java.util.List;

@Data
public class DeliveryResponseDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String region;
    private int postCode;
    private double totalCost;

    private CityWithoutDeliveriesDTO city;
    private int houseNumber;
    private int floor;
    private int apartmentNumber;
    private Boolean needsBill;
    private String extraInformation;
    private List<OrderResponseDTO> orders;
}
