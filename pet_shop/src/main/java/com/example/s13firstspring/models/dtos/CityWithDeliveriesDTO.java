package com.example.s13firstspring.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class CityWithDeliveriesDTO implements Serializable {
    private long id;
    private String city_name;
    private List<DeliveryWithoutCityDTO> deliveries;
}
