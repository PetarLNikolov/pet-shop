package com.example.s13firstspring.models.dtos.cities;

import com.example.s13firstspring.models.dtos.deliveries.DeliveryResponseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class CityWithDeliveriesDTO implements Serializable {
    private long id;
    private String city_name;
    private List<DeliveryResponseDTO> deliveries;
}
