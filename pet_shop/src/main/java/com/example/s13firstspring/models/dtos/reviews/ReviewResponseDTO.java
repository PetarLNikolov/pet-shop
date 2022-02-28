package com.example.s13firstspring.models.dtos.reviews;

import com.example.s13firstspring.models.dtos.products.ProductWithIdAndName;
import com.example.s13firstspring.models.entities.User;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReviewResponseDTO {
    private long id;
    private int rating;
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class )
    private LocalDateTime createdAt;

    private ProductWithIdAndName product;



}
