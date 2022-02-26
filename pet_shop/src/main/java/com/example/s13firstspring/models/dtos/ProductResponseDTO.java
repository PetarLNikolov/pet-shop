package com.example.s13firstspring.models.dtos;

import com.example.s13firstspring.models.entities.Review;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import java.util.HashSet;
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
