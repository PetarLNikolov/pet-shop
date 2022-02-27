package com.example.s13firstspring.models.dtos.categories;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // @ToString , @EqualsAndHashCode , @Getter / @Setter and @RequiredArgsConstructor
public class CategoryWithoutSubcategoriesDTO {
    private long id;
    private String name;

}
