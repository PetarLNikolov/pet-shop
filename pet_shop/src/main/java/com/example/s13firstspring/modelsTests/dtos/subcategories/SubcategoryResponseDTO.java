package com.example.s13firstspring.modelsTests.dtos.subcategories;

import com.example.s13firstspring.modelsTests.dtos.categories.CategoryWithoutSubcategoriesDTO;
import com.example.s13firstspring.modelsTests.dtos.products.ProductWithoutUserDTO;
import lombok.Data;

import java.util.Set;

@Data
public class SubcategoryResponseDTO {
    private int id;
    private String name;
    private CategoryWithoutSubcategoriesDTO category;
    private Set<ProductWithoutUserDTO> products;

}
