package com.example.s13firstspring.modelsTests.dtos.discounts;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class DiscountWithoutProducts {
    private String name;
    private int percentDiscount;
    private LocalDateTime startOfOffer;
    private LocalDateTime endOfOffer;
}
