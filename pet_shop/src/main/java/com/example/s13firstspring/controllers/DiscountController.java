package com.example.s13firstspring.controllers;



import com.example.s13firstspring.models.entities.Discount;

import com.example.s13firstspring.services.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@RestController
public class DiscountController {
    @Autowired
    private DiscountService discountService;

    //-add discount
    @PostMapping("/add")
    public ResponseEntity<Discount> add(@RequestBody Discount discount,HttpSession session, HttpServletRequest request) {
        UserController.validateLogin(session, request);
        String name = discount.getName();
        int percentDiscount = discount.getPercentDiscount();
        LocalDateTime startDate=discount.getStartOfOffer();
        LocalDateTime endDate=discount.getEndOfOffer();
        Discount d = discountService.add(name,percentDiscount,startDate,endDate);
        return ResponseEntity.ok(d);
    }
    //-edit discount
    @PutMapping("/edit")
    public ResponseEntity<Discount> edit(@RequestBody Discount discount, HttpSession session, HttpServletRequest request) {
        UserController.validateLogin(session, request);

        Discount d = discountService.edit(discount);
        return ResponseEntity.ok(d);
    }
    //-remove discount
    @DeleteMapping("/delete")
    public ResponseEntity<Discount>  delete(@RequestBody Discount discount, HttpSession session, HttpServletRequest request){
        UserController.validateLogin(session, request);
        String name = discount.getName();
        Discount d=discountService.delete(name);
        return ResponseEntity.accepted().body(d);
    }
    //-send info about discount of liked product
}
