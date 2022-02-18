package com.example.s13firstspring.models.dtos;

import com.example.s13firstspring.models.Product;
import lombok.Data;

import javax.persistence.Column;
import java.util.Set;

@Data
public class BrandResponseDTO {

    private long id;
    private String brandName;
    private String logoURL;
    private Set<ProductResponseDTO> products;
}
