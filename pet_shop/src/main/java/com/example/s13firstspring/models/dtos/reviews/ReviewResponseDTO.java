package com.example.s13firstspring.models.dtos.reviews;

import com.example.s13firstspring.models.dtos.products.ProductWithIdAndName;
import com.example.s13firstspring.models.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReviewResponseDTO {
    private long id;
    private int rating;
    private LocalDateTime createdAt;
    private int userId;
    private ProductWithIdAndName product;

    public ReviewResponseDTO(long id, int rating, LocalDateTime createdAt, User user, ProductWithIdAndName product) {
        this.id = id;
        this.rating = rating;
        this.createdAt = createdAt;
        this.userId = user.getId();
        this.product = product;
    }

}
