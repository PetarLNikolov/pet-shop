package com.example.s13firstspring.models.dtos.users;


import lombok.Data;

@Data
public class UserWithNameAndIdDTO {
    private int id;
    private String fullName;

}
