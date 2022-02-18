package com.example.s13firstspring.models.dtos;

import com.example.s13firstspring.models.Product;
import lombok.Data;

import javax.persistence.Column;
import java.util.Set;

@Data
public class AnimalResponseDTO {

    private long id;

    private String name;

    private Set<ProductResponseDTO> products;

}
