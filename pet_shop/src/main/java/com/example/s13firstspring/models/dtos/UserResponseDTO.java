package com.example.s13firstspring.models.dtos;

import com.example.s13firstspring.models.entities.Product;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserResponseDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Date dateOfBirth;
    private String username;
    Set<ProductWithoutUserDTO> favouriteProducts;
}
