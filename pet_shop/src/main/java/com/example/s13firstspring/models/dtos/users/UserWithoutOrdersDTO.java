package com.example.s13firstspring.models.dtos.users;

import lombok.Data;

import java.util.Date;
@Data
public class UserWithoutOrdersDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Date dateOfBirth;
    private String username;
}
