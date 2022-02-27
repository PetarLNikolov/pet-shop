package com.example.s13firstspring.models.dtos.animals;

import com.example.s13firstspring.models.dtos.products.ProductResponseDTO;
import lombok.Data;

import java.util.Set;

@Data
public class AnimalResponseDTO {

    private long id;

    private String name;

    private Set<ProductResponseDTO> products;

}
