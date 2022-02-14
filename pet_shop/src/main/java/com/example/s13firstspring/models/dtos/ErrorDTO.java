package com.example.s13firstspring.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class ErrorDTO {

    private String msg;
    private int status;

}
