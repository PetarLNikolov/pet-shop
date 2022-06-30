package com.example.s13firstspring.modelsTests.dtos.reviews;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewWithoutProductDTO {
    private int id;
    private int rating;
    private LocalDateTime createdAt;



}
