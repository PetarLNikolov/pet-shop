package com.example.s13firstspring.controllers;

import com.example.s13firstspring.models.dtos.*;
import com.example.s13firstspring.models.entities.City;
import com.example.s13firstspring.services.CityService;
import com.example.s13firstspring.services.utilities.LoginUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class CityController {

    @Autowired
    private CityService cityService;

    @PostMapping("/cities/add")
    public City addCity(@RequestBody CityAddDTO city, HttpServletRequest request) {
        LoginUtility.validateLogin(request);
        return cityService.add(city);
    }

    //-edit discount
    @PutMapping("/cities/edit")
    public ResponseEntity<City> edit(@RequestBody City city, HttpServletRequest request) {
        LoginUtility.validateLogin(request);
        return ResponseEntity.ok(cityService.edit(city));
    }

    //-remove discount
    @DeleteMapping("/cities/delete")
    public ResponseEntity<CityReturnDTO> delete(@RequestBody City city, HttpServletRequest request) {
        LoginUtility.validateLogin(request);
        return ResponseEntity.accepted().body(cityService.delete(city));
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CityWithDeliveriesDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(cityService.getById(id));
    }
}
