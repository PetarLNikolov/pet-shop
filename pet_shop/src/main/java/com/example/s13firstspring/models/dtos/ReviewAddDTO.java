package com.example.s13firstspring.models.dtos;

import com.example.s13firstspring.models.entities.Product;
import com.example.s13firstspring.models.entities.User;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewAddDTO {
    private int rating;

    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;
    private User user;
    private Product product;
}
