package com.example.s13firstspring.models.dtos;

import lombok.Data;

@Data
public class BrandWithoutProductsDTO {
    private long id;
    private String brandName;
    private String logoURL;
}
