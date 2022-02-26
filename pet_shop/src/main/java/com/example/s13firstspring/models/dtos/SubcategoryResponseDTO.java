package com.example.s13firstspring.models.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class SubcategoryResponseDTO {
    private int id;
    private String name;
    private CategoryWithoutSubcategoriesDTO category;
    private Set<ProductWithoutUserDTO> products;

}
