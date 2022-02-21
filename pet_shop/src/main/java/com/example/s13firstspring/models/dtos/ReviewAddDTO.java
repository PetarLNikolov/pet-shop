package com.example.s13firstspring.models.dtos;

import com.example.s13firstspring.models.entities.Product;
import com.example.s13firstspring.models.entities.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewAddDTO {
    private int rating;
    private LocalDateTime createdAt;
    private User user;  //TODO ask if it is better to work with id instead of object
    private Product product;
}
