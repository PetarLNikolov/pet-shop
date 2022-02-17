package com.example.s13firstspring.models.dtos;

import com.example.s13firstspring.models.entities.Delivery;
import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;

@Data
public class CityAddDTO {

    @Column
    private String city_name;

}
