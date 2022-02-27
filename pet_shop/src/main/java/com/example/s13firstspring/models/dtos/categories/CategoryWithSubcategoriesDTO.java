package com.example.s13firstspring.models.dtos.categories;

import com.example.s13firstspring.models.dtos.subcategories.SubcategoryWithoutCategoryDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class CategoryWithSubcategoriesDTO {
    private long id;
    private String name;
    private List<SubcategoryWithoutCategoryDTO> subcategories;
}
