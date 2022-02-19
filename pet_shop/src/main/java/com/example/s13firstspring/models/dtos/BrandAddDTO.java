package com.example.s13firstspring.models.dtos;

import lombok.Data;

import javax.persistence.Column;

@Data
public class BrandAddDTO {
    private long id;

    private String brandName;

    private String logoURL;
}
