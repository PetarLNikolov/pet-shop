package com.example.s13firstspring.models.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Data
@NoArgsConstructor
public class CategoryResponseDTO {
    private long id;
    private String name;
    private List<SubcategoryWithoutCategoryDTO> subcategories;
}
