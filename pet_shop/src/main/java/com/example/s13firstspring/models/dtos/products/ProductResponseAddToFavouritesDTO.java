package com.example.s13firstspring.models.dtos.products;

import lombok.Data;

@Data
public class ProductResponseAddToFavouritesDTO {

    private int id;

    private String name;

    private double rating;

    private int fans;
}
