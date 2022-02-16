package com.example.s13firstspring.controllers;

import com.example.s13firstspring.models.entities.Animal;
import com.example.s13firstspring.models.entities.Brand;
import com.example.s13firstspring.models.repositories.BrandRepository;
import com.example.s13firstspring.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class BrandController {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandService brandService;

    @PostMapping("/animals")
    public Brand addBrand(@RequestBody Brand brand) {
        brandRepository.save(brand);
        return brand;
    }
    //-remove brand
    @DeleteMapping("/delete")
    public ResponseEntity<Brand> delete(@RequestBody Brand brand, HttpSession session, HttpServletRequest request){
        UserController.validateLogin(session, request);

        String name = brand.getBrandName();
        Brand b= brandService.delete(name);
        return ResponseEntity.accepted().body(b);
    }
    //-edit brand
    @PutMapping("/edit")
    public ResponseEntity<Brand> edit(@RequestBody Brand brand, HttpSession session, HttpServletRequest request) {
        UserController.validateLogin(session, request);
        Brand b=brandService.edit(brand);
        return ResponseEntity.ok(b);
    }
}
