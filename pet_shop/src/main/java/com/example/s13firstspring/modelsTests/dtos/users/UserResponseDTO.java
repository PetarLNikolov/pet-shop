package com.example.s13firstspring.modelsTests.dtos.users;

import com.example.s13firstspring.modelsTests.dtos.products.ProductWithoutUserDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

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
