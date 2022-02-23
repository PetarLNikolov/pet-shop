package com.example.s13firstspring.models.dtos;

import lombok.Data;

import java.util.Map;

@Data
public class OrderWithProductAndUnitsDTO {
    //productName->units
    Map<String,Integer> productsInOrder;
    int orderId;
    double cost;
}
