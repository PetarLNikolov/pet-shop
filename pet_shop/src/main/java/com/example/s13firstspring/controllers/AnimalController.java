package com.example.s13firstspring.controllers;


import com.example.s13firstspring.models.dtos.AnimalAddDTO;
import com.example.s13firstspring.models.dtos.AnimalResponseDTO;
import com.example.s13firstspring.services.AnimalService;
import com.example.s13firstspring.services.utilities.SessionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AnimalController {

        @Autowired
        private AnimalService service;

        @PostMapping("/animals/add")
        public ResponseEntity<AnimalResponseDTO> addAnimal(@RequestBody AnimalAddDTO animal, HttpServletRequest request) {
            SessionUtility.validateLogin(request);
            SessionUtility.isAdmin(request);

            return ResponseEntity.ok(service.add(animal));
        }

    @DeleteMapping("/animals/delete/{id}")
    public void delete(@PathVariable int id, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        SessionUtility.isAdmin(request);
        service.delete(id);
    }


}
