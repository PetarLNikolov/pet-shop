package com.example.s13firstspring.models.dtos;

import com.example.s13firstspring.models.entities.Image;
import lombok.Data;

import java.util.Set;

@Data
public class ProductResponseDTO {

    private int id;

    private String name;

    private double price;

    private double discountPrice;

    private int unitsInStock;

    private String description;

    private double rating;

    private BrandWithoutProductsDTO brand;

    private SubcategoryWithoutProductsDTO subcategory;

    private AnimalWithoutProductsDTO animal;

    private DiscountWithoutProducts discount;

    private String imageUrl;

}
