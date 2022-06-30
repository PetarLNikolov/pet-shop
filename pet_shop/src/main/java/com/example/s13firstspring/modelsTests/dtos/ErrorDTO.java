package com.example.s13firstspring.modelsTests.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ErrorDTO {

    private String msg;
    private int status;

}
