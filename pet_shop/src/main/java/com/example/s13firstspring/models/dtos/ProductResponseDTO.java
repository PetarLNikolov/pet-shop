package com.example.s13firstspring.models.dtos;

import lombok.Data;

@Data
public class ProductResponseDTO {

    private int id;

    private String name;

    private double price;

    private int unitsInStock;

    private String description;

    private double rating;

    private BrandWithoutProductsDTO brand;

    private SubcategoryWithoutProductsDTO subcategory;

    private AnimalWithoutProductsDTO animal;

    private String imageURLp;

    private DiscountWithoutProducts discount;

}
