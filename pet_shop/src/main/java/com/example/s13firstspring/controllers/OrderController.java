package com.example.s13firstspring.controllers;


import com.example.s13firstspring.services.OrderService;
import com.example.s13firstspring.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;


}
