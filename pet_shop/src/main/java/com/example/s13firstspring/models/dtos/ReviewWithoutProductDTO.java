package com.example.s13firstspring.models.dtos;

import com.example.s13firstspring.models.entities.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewWithoutProductDTO {
    private int id;
    private int rating;
    private LocalDateTime createdAt;



}
