package com.example.s13firstspring.controllers;

import com.example.s13firstspring.models.Animal;
import com.example.s13firstspring.models.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnimalController {

        @Autowired
        private AnimalRepository animalRepository;

        @PostMapping("/animals")
        public Animal addAnimal(@RequestBody Animal animal) {
            animalRepository.save(animal);
            return animal;
        }
}
