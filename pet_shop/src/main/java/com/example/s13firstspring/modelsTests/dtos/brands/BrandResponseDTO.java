package com.example.s13firstspring.modelsTests.dtos.brands;

import com.example.s13firstspring.modelsTests.dtos.products.ProductResponseDTO;
import lombok.Data;

import java.util.Set;

@Data
public class BrandResponseDTO {

    private long id;
    private String brandName;
    private String logoURL;
    private Set<ProductResponseDTO> products;
}
