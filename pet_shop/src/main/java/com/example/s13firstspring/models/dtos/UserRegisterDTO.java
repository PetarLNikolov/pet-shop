package com.example.s13firstspring.models.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class UserRegisterDTO {

    private String username;
    private String password;
    private String password2;
}
