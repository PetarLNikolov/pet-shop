package com.example.s13firstspring.models.dtos;

import com.example.s13firstspring.models.entities.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubcategoryWithCategoryDTO {
    private int id;
    private String name;
    private CategoryWithoutSubcategoriesDTO category;

}
