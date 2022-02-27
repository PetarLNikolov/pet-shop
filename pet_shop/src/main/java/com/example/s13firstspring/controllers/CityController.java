package com.example.s13firstspring.controllers;

import com.example.s13firstspring.models.dtos.cities.CityAddDTO;
import com.example.s13firstspring.models.dtos.cities.CityWithDeliveriesDTO;
import com.example.s13firstspring.models.entities.City;
import com.example.s13firstspring.services.CityService;
import com.example.s13firstspring.services.utilities.SessionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CityController {

    @Autowired
    private CityService cityService;

    @PostMapping("/cities/add")
    public City addCity(@RequestBody CityAddDTO city, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        return cityService.add(city);
    }

    //-edit discount
    @PutMapping("/cities/edit")
    public ResponseEntity<City> edit(@RequestBody City city, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        return ResponseEntity.ok(cityService.edit(city));
    }

    //-remove discount
    @DeleteMapping("/cities/delete/{id}")
    public void delete(@PathVariable int id, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        SessionUtility.isAdmin(request);
        cityService.delete(id);
    }

    @GetMapping("/cities/{id}")
    public ResponseEntity<CityWithDeliveriesDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(cityService.getById(id));
    }
}
