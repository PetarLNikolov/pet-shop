package com.example.s13firstspring.models.dtos;

import com.example.s13firstspring.models.entities.Discount;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
public class ProductAddDTO {

    private String name;

    private double price;

    private int unitsInStock;

    private int subcategoryId;

    private int brandId;

    private int animalId;

    private String description;

    private LocalDateTime createdAt;

    private int discountId;

}
