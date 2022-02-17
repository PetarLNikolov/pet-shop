package com.example.s13firstspring.models.dtos;

import lombok.Data;

@Data
public class SubcategoryResponseDTO {
    private int id;
    private String name;
    private CategoryWithSubcategoriesDTO category;

}
