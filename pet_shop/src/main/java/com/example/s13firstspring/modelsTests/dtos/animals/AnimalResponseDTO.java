package com.example.s13firstspring.modelsTests.dtos.animals;

import com.example.s13firstspring.modelsTests.dtos.products.ProductResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class AnimalResponseDTO {

    private long id;

    private String name;

    private Set<ProductResponseDTO> products;

}
