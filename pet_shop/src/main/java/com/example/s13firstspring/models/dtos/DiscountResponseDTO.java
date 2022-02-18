package com.example.s13firstspring.models.dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class DiscountResponseDTO {
    private String name;
    private int percentDiscount;
    private LocalDateTime startOfOffer;
    private LocalDateTime endOfOffer;
    private Set<ProductResponseDTO> products;
}
