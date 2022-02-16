package com.example.s13firstspring.controllers;

import com.example.s13firstspring.models.entities.Animal;
import com.example.s13firstspring.models.entities.Category;
import com.example.s13firstspring.models.entities.Discount;
import com.example.s13firstspring.models.entities.Product;
import com.example.s13firstspring.models.repositories.AnimalRepository;
import com.example.s13firstspring.services.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class AnimalController {

        @Autowired
        private AnimalRepository animalRepository;

        @Autowired
        private AnimalService animalService;

        @PostMapping("/animals")
        public Animal addAnimal(@RequestBody Animal animal) {
            animalRepository.save(animal);
            return animal;
        }
    //-remove animal
    @DeleteMapping("/delete")
    public ResponseEntity<Animal> delete(@RequestBody Animal animal, HttpSession session, HttpServletRequest request){
      UserController.validateLogin(session, request);

        String name = animal.getName();
        Animal a= animalService.delete(name);
        return ResponseEntity.accepted().body(a);
    }
    //-edit animal
    @PutMapping("/edit")
    public ResponseEntity<Animal> edit(@RequestBody Animal animal, HttpSession session, HttpServletRequest request) {
        UserController.validateLogin(session, request);
        Animal a=animalService.edit(animal);
        return ResponseEntity.ok(a);
    }
}
