package com.example.s13firstspring.models.dtos;

import com.example.s13firstspring.models.entities.Image;
import lombok.Data;

import java.util.Set;

@Data
public class ProductWithoutUserDTO {
    private int id;

    private String name;

    private double price;

    private String description;



}
