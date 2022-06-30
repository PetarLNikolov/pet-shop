package com.example.s13firstspring.modelsTests.dtos.subcategories;

import com.example.s13firstspring.modelsTests.dtos.categories.CategoryWithoutSubcategoriesDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubcategoryWithCategoryDTO {
    private int id;
    private String name;
    private CategoryWithoutSubcategoriesDTO category;

}
