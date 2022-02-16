package com.example.s13firstspring.services;

import com.example.s13firstspring.exceptions.BadRequestException;
import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.models.entities.Animal;
import com.example.s13firstspring.models.repositories.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    public Animal add(Animal animal) {
        if(animalRepository.findByName(animal.getName()) != null){
            throw new BadRequestException("Animal name already exists");
        }
        Animal a = new Animal();
        animalRepository.save(a);
        return a;
    }

    public Animal delete(String name) {
        if(animalRepository.findByName(name) == null){
            throw new NotFoundException("Animal not found");
        }
        Animal animal1 = new Animal();
        animal1.setName(name);
        animalRepository.delete(animal1);
        return animal1;
    }

    @Transactional
    public Animal edit(Animal animal) {
        Optional<Animal> opt = animalRepository.findById(animal.getId());
        if (opt.isPresent()) {
            animalRepository.save(animal);
            return animal;
        } else {
            throw new NotFoundException("Animal not found");
        }
    }
}
