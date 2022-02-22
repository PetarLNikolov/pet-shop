package com.example.s13firstspring.controllers;



import com.example.s13firstspring.models.dtos.*;
import com.example.s13firstspring.models.entities.*;
import com.example.s13firstspring.models.repositories.DeliveryRepository;
import com.example.s13firstspring.models.repositories.OrderRepository;
import com.example.s13firstspring.models.repositories.ProductRepository;
import com.example.s13firstspring.services.OrderService;
import com.example.s13firstspring.services.utilities.LoginUtility;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;


    @PostMapping("/orders/add")
    public ResponseEntity<OrderAddDTO> add(@RequestBody HttpServletRequest request) {
        LoginUtility.validateLogin(request);
        return ResponseEntity.ok(orderService.add());
    }

    //-edit order
    @PutMapping("/orders/edit")
    public ResponseEntity<Order> edit(@RequestBody Order order, HttpServletRequest request) {
        LoginUtility.validateLogin(request);
        return ResponseEntity.ok(orderService.edit(order));
    }


    //-remove order
    @DeleteMapping("/orders/delete/{id}")
    public void delete(@PathVariable int id, HttpServletRequest request) {
        LoginUtility.validateLogin(request);
        orderService.delete(id);
    }





}
