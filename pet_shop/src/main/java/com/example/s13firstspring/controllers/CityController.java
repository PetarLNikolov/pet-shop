package com.example.s13firstspring.controllers;

import com.example.s13firstspring.models.entities.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.s13firstspring.models.repositories.CityRepository;

@RestController
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @PostMapping("/products")
    public City addCity(@RequestBody City city) {
        cityRepository.save(city);
        return city;
    }
}
