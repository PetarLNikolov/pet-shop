package com.example.s13firstspring.controllers;

import com.example.s13firstspring.models.dtos.DeliveryEditDTO;
import com.example.s13firstspring.models.dtos.DeliveryResponseDTO;
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


    //-edit delivery
    @PutMapping("/deliveries/edit/{id}")
    public ResponseEntity<DeliveryResponseDTO> edit(@RequestBody DeliveryEditDTO delivery, @RequestParam int id, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        return ResponseEntity.ok(deliveryService.edit(delivery,id));
    }

    //-remove delivery
    @DeleteMapping("/deliveries/delete/{id}")
    public void delete(@PathVariable int id, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        deliveryService.deleteDelivery(id);
    }
    @PutMapping("/deliveries/send-delivery/{id}")
    public String sendDelivery(@PathVariable int id,HttpServletRequest request){
        SessionUtility.validateLogin(request);
        return deliveryService.sendDelivery(id,request);
    }
}
