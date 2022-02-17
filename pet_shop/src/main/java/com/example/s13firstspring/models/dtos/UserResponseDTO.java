package com.example.s13firstspring.models.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class UserResponseDTO {

    private int id;
    private String username;
}
