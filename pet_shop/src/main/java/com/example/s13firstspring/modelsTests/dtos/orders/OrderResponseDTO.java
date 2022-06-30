package com.example.s13firstspring.modelsTests.dtos.orders;

import com.example.s13firstspring.modelsTests.dtos.users.UserWithoutOrdersDTO;
import lombok.Data;

@Data
public class OrderResponseDTO {
    int id;
    UserWithoutOrdersDTO user;

}
