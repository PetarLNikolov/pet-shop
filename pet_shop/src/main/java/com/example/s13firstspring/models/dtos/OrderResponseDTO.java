package com.example.s13firstspring.models.dtos;

import com.example.s13firstspring.models.entities.OrdersHaveProductsKey;
import lombok.Data;

@Data
public class OrderResponseDTO {
    int id;
    UserWithoutOrdersDTO user;

}
