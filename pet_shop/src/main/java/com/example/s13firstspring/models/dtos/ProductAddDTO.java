package com.example.s13firstspring.models.dtos;


import lombok.Data;


@Data
public class ProductAddDTO {

    private String name;

    private double price;

    private int unitsInStock;

    private String description;

    private double rating;
}
