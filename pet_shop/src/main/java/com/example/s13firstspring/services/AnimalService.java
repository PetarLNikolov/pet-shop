package com.example.s13firstspring.services;

import com.example.s13firstspring.exceptions.BadRequestException;
import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.models.dtos.animals.AnimalAddDTO;
import com.example.s13firstspring.models.dtos.animals.AnimalResponseDTO;
import com.example.s13firstspring.models.entities.Animal;
import com.example.s13firstspring.models.repositories.AnimalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {

    @Autowired
    AnimalRepository repository;
    @Autowired
    ModelMapper mapper;

    public AnimalResponseDTO add(AnimalAddDTO animal) {
        String name=animal.getName();
        if(name == null || name.isBlank()){
            throw new BadRequestException("Name is mandatory");
        }
        if(repository.findByName(name) != null){
            throw new BadRequestException("Animal already exists");
        }
        Animal a = new Animal();
        a.setName(animal.getName());
        repository.save(a);
        return mapper.map(a,AnimalResponseDTO.class);
    }

    public void delete(int id) {
        repository.delete(repository.findById(id).orElseThrow(()->new NotFoundException("Animal not found")));
    }
}
