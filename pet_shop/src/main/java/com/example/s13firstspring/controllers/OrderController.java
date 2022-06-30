package com.example.s13firstspring.controllers;


import com.example.s13firstspring.modelsTests.dtos.deliveries.DeliveryResponseDTO;
import com.example.s13firstspring.modelsTests.dtos.orders.OrderWithProductAndUnitsDTO;
import com.example.s13firstspring.services.OrderService;
import com.example.s13firstspring.services.utilities.SessionUtility;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;



//    public ResponseEntity<OrderResponseDTO> add(@RequestBody OrderAddDTO order, HttpServletRequest request) {
//        SessionUtility.validateLogin(request);
//        return ResponseEntity.ok(orderService.add(order, request));
//    }

    @PutMapping("/orders/addProduct/{productId}")
    public ResponseEntity<OrderWithProductAndUnitsDTO> addProduct(@PathVariable("productId") int product_id, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        return ResponseEntity.ok().body(orderService.addProduct(product_id, request));
    }

    @PutMapping("/orders/removeProduct/{productId}")
    public ResponseEntity<OrderWithProductAndUnitsDTO> removeProduct(@PathVariable("productId") int product_id, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        return ResponseEntity.ok().body(orderService.removeProduct(product_id, request));
    }

    @PostMapping("/orders/finalizeOrder/{orderId}/delivery/{deliveryId}")
    public ResponseEntity<DeliveryResponseDTO> finalizeOrder(@PathVariable("orderId") int orderId, @PathVariable("deliveryId") int deliveryId, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        return ResponseEntity.ok().body(orderService.finalizeOrder(orderId, deliveryId,request));
    }


    @DeleteMapping("/orders/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Deleted product: " + orderService.delete(id));
    }


}
