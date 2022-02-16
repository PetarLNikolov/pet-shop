package com.example.s13firstspring.controllers;

import com.example.s13firstspring.models.Brand;
import com.example.s13firstspring.models.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrandController {

    @Autowired
    private BrandRepository brandRepository;

    @PostMapping("/animals")
    public Brand addBrand(@RequestBody Brand brand) {
        brandRepository.save(brand);
        return brand;
    }
}
