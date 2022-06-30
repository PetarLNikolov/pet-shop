package com.example.s13firstspring.modelsTests.dtos.categories;

import com.example.s13firstspring.modelsTests.dtos.subcategories.SubcategoryWithoutCategoryDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class CategoryResponseDTO {
    private long id;
    private String name;
    private List<SubcategoryWithoutCategoryDTO> subcategories;
}
