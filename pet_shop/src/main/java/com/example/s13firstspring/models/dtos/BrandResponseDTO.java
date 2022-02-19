package com.example.s13firstspring.models.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class BrandResponseDTO {

    private long id;
    private String brandName;
    private String logoURL;
    private Set<ProductResponseDTO> products;
}
