package com.example.s13firstspring.models.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@NoArgsConstructor
public class ErrorDTO {

    private String msg;
    private int status;

}
