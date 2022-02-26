package com.example.s13firstspring.controllers;


import com.example.s13firstspring.models.dtos.DiscountAddDTO;
import com.example.s13firstspring.models.dtos.DiscountResponseDTO;
import com.example.s13firstspring.models.entities.Discount;

import com.example.s13firstspring.services.DiscountService;
import com.example.s13firstspring.services.utilities.SessionUtility;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DiscountController {
    @Autowired
    private DiscountService discountService;

    //-add discount
    @PostMapping("/discounts/add")

    public ResponseEntity<DiscountResponseDTO> add(@RequestBody DiscountAddDTO discount, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        SessionUtility.isAdmin(request);
        return ResponseEntity.ok(discountService.add(discount));
    }

    //-edit discount
    @PutMapping("/discounts/edit/{id}")
    public ResponseEntity<Discount> edit(@PathVariable int id,@RequestBody DiscountAddDTO discount, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        SessionUtility.isAdmin(request);
        return ResponseEntity.ok(discountService.edit(discount,id));
    }

    //-remove discount
    @DeleteMapping("/discounts/delete/{id}")
    public void delete(@PathVariable int id, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        SessionUtility.isAdmin(request);
        discountService.delete(id);
        request.getSession().invalidate();
    }

}
