package com.example.s13firstspring.models.dtos.orders;

import com.example.s13firstspring.models.dtos.users.UserWithoutOrdersDTO;
import lombok.Data;

@Data
public class OrderResponseDTO {
    int id;
    UserWithoutOrdersDTO user;

}
