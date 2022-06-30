package com.example.s13firstspring.modelsTests.dtos.products;

import lombok.Data;


@Data
public class ProductAddDTO {

    private String name;

    private double price;

    private int unitsInStock;

    private int subcategoryId;

    private int brandId;

    private int animalId;

    private String description;

    private int discountId;

}
