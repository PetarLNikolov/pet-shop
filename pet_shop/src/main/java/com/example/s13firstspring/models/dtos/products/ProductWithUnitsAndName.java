package com.example.s13firstspring.models.dtos.products;

import lombok.Data;

@Data
public class ProductWithUnitsAndName {
    private int id;

    private String name;

    private int unitsInStock;
}
