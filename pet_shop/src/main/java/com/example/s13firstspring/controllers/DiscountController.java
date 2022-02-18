package com.example.s13firstspring.controllers;


import com.example.s13firstspring.models.dtos.DiscountAddDTO;
import com.example.s13firstspring.models.dtos.DiscountResponseDTO;
import com.example.s13firstspring.models.entities.Discount;

import com.example.s13firstspring.services.DiscountService;
import com.example.s13firstspring.services.utilities.LoginUtility;
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
    @PostMapping("/discounts/add")
    public ResponseEntity<DiscountResponseDTO> add(@RequestBody DiscountAddDTO discount, HttpServletRequest request) {
        LoginUtility.validateLogin(request);
        LoginUtility.isAdmin(request);
        return ResponseEntity.ok(discountService.add(discount));
    }

    //-edit discount
    @PutMapping("/discounts/edit")
    public ResponseEntity<Discount> edit(@RequestBody Discount discount, HttpServletRequest request) {
        LoginUtility.validateLogin(request);
        LoginUtility.isAdmin(request);
        Discount d = discountService.edit(discount);
        return ResponseEntity.ok(d);
    }

    //-remove discount
    @DeleteMapping("/discounts/delete/{id}")
    public void delete(@PathVariable int id, HttpServletRequest request) {
        LoginUtility.validateLogin(request);
        LoginUtility.isAdmin(request);
        discountService.delete(id);
    }
    //-send info about discount of liked product
}
