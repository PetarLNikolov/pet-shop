package com.example.s13firstspring.models.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DiscountAddDTO {
    private String name;
    private int percentDiscount;
    private LocalDateTime startOfOffer;
    private LocalDateTime endOfOffer;


}
