package com.example.s13firstspring.models.dtos.products;

import com.example.s13firstspring.models.dtos.ImageWithoutProductDTO;
import com.example.s13firstspring.models.dtos.reviews.ReviewWithoutProductDTO;
import com.example.s13firstspring.models.dtos.subcategories.SubcategoryWithoutProductsDTO;
import com.example.s13firstspring.models.dtos.brands.BrandWithoutProductsDTO;
import com.example.s13firstspring.models.dtos.animals.AnimalWithoutProductsDTO;
import com.example.s13firstspring.models.dtos.discounts.DiscountWithoutProducts;
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

    private Set<ReviewWithoutProductDTO> reviews;

    private BrandWithoutProductsDTO brand;

    private SubcategoryWithoutProductsDTO subcategory;

    private AnimalWithoutProductsDTO animal;

    private DiscountWithoutProducts discount;

    private Set<ImageWithoutProductDTO> images;


}
