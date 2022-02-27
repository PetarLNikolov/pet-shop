package com.example.s13firstspring.models.dtos.orders;

import lombok.Data;

import java.util.Map;

@Data
public class OrderWithProductAndUnitsDTO {
    //productName->units
    Map<String,Integer> productsInOrder; //TODO add productId here
    int orderId;
    double cost;
}
