package com.example.s13firstspring.models.dtos;

import com.example.s13firstspring.models.entities.Product;
import com.example.s13firstspring.models.entities.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResponseDTO {
    private long id;
    private int rating;
    private LocalDateTime createdAt;
    private UserResponseDTO user;
    private ProductResponseDTO product;

}
