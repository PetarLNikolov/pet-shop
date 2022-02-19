package com.example.s13firstspring.controllers;

import com.example.s13firstspring.models.dtos.DeliveryWithoutCityDTO;
import com.example.s13firstspring.models.dtos.OrderAddDTO;
import com.example.s13firstspring.models.entities.Delivery;
import com.example.s13firstspring.models.entities.Order;
import com.example.s13firstspring.models.repositories.DeliveryRepository;
import com.example.s13firstspring.services.DeliveryService;
import com.example.s13firstspring.services.utilities.LoginUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @PostMapping("/orders/add")
    public ResponseEntity<DeliveryWithoutCityDTO> add(@RequestBody DeliveryWithoutCityDTO delivery, HttpServletRequest request) {
        LoginUtility.validateLogin(request);
        return ResponseEntity.ok(deliveryService.add(delivery));
    }

    //-edit delivery
    @PutMapping("/delivery/edit")
    public ResponseEntity<Delivery> edit(@RequestBody Delivery delivery, HttpServletRequest request) {
        LoginUtility.validateLogin(request);
        return ResponseEntity.ok(deliveryService.edit(delivery));
    }

    //-remove delivery
    @DeleteMapping("/delivery/delete/{id}")
    public void delete(@PathVariable int id, HttpServletRequest request) {
        LoginUtility.validateLogin(request);
        deliveryService.delete(id);
    }
}
