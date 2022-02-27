package com.example.s13firstspring.models.dtos.subcategories;

import com.example.s13firstspring.models.dtos.categories.CategoryWithoutSubcategoriesDTO;
import com.example.s13firstspring.models.dtos.products.ProductWithoutUserDTO;
import lombok.Data;

import java.util.Set;

@Data
public class SubcategoryResponseDTO {
    private int id;
    private String name;
    private CategoryWithoutSubcategoriesDTO category;
    private Set<ProductWithoutUserDTO> products;

}
