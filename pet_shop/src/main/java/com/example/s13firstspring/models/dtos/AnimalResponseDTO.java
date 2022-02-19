package com.example.s13firstspring.models.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class AnimalResponseDTO {

    private long id;

    private String name;

    private Set<ProductResponseDTO> products;

}
