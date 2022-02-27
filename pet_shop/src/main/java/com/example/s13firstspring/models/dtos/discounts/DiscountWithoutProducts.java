package com.example.s13firstspring.models.dtos.discounts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class DiscountWithoutProducts {
    private String name;
    private int percentDiscount;
    private LocalDateTime startOfOffer;
    private LocalDateTime endOfOffer;
}
