package com.example.s13firstspring.controllers;

import com.example.s13firstspring.models.dtos.BrandAddDTO;
import com.example.s13firstspring.models.dtos.BrandResponseDTO;
import com.example.s13firstspring.services.BrandService;
import com.example.s13firstspring.services.utilities.LoginUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BrandController {

    @Autowired
    private BrandService service;

    @PostMapping("/brands/add")
    public ResponseEntity<BrandResponseDTO> addBrand(@RequestBody BrandAddDTO brand, HttpServletRequest request) {
        LoginUtility.validateLogin(request);
        LoginUtility.isAdmin(request);
        return ResponseEntity.ok(service.save(brand));
    }

    @PutMapping("brand/edit/{id}")
    public ResponseEntity<BrandResponseDTO> editBrand(@PathVariable int id,HttpServletRequest request){
        LoginUtility.validateLogin(request);
        LoginUtility.isAdmin(request);
        return ResponseEntity.ok(service.edit(id));
    }
}
