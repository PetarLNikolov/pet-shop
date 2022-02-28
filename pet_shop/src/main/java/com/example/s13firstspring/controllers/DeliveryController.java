package com.example.s13firstspring.controllers;

import com.example.s13firstspring.models.dtos.deliveries.DeliveryEditDTO;
import com.example.s13firstspring.models.dtos.deliveries.DeliveryResponseDTO;
import com.example.s13firstspring.services.DeliveryService;
import com.example.s13firstspring.services.utilities.SessionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;
    //TODO check for every request if the delivery is owned by the Session.utility,getuserid; (jdbctemplate- with 1 request)

    //-edit delivery
    @PutMapping("/deliveries/edit/{id}")//TODO use typemapper
    public ResponseEntity<DeliveryResponseDTO> edit(@RequestBody DeliveryEditDTO delivery, @PathVariable int id, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        return ResponseEntity.ok(deliveryService.edit(delivery,id));
    }

    @PutMapping("/deliveries/send-delivery/{id}")
    public ResponseEntity<DeliveryResponseDTO> sendDelivery(@PathVariable int id,HttpServletRequest request){
        SessionUtility.validateLogin(request);
        return ResponseEntity.ok().body(deliveryService.sendDelivery(id,request));
    }
    @GetMapping("/deliveries/{id}")
    public ResponseEntity<DeliveryResponseDTO> getById(@PathVariable int id,HttpServletRequest request){
        SessionUtility.validateLogin(request);
        return ResponseEntity.ok().body(deliveryService.getById(id));
    }
}
