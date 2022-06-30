package com.example.s13firstspring.services;

import com.example.s13firstspring.exceptions.BadRequestException;
import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.modelsTests.dtos.animals.AnimalAddDTO;
import com.example.s13firstspring.modelsTests.dtos.animals.AnimalResponseDTO;
import com.example.s13firstspring.modelsTests.entities.Animal;
import com.example.s13firstspring.modelsTests.repositories.AnimalRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.rmi.NotBoundException;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class AnimalServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private AnimalAddDTO animalAddDTO;

    @InjectMocks
    private AnimalService animalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void add() {

    }

    @Test
    void shouldThrowWhenNotFound() {
        Animal animal = new Animal(1, "Animal", new HashSet<>());
        when(animalRepository.findById(2)).thenThrow(new NotFoundException("Animal not found"));
        Throwable exception = assertThrows(NotFoundException.class, () -> animalService.delete(2));
        assertEquals(exception.getMessage(), "Animal not found");
    }

    @Test
    void shouldThrowWhenNameIsBlank() {
        AnimalAddDTO animal = new AnimalAddDTO();
        Throwable exception = assertThrows(BadRequestException.class, () -> animalService.add(animal));
        assertEquals(exception.getMessage(), "Name is mandatory");
    }

    @Test
    void shouldThrowWhenAnimalExists() {
        AnimalAddDTO animal = new AnimalAddDTO("Horse");
        when(animalRepository.findByName("Duck")).thenThrow(new BadRequestException("Animal already exists"));
        Throwable exception = assertThrows(BadRequestException.class, () -> animalService.add(new AnimalAddDTO("Duck")));
        assertEquals(exception.getMessage(), "Animal already exists");
    }

    @Test
    void shouldReturnAnimalWhenAllOk() {
        AnimalAddDTO animal = new AnimalAddDTO("Horse");
        AnimalResponseDTO animalResponse = new AnimalResponseDTO(1, "Horse", new HashSet<>());
        when(modelMapper.map(any(),any())).thenReturn(animalResponse);
        assertEquals(animalResponse, animalService.add(animal));
    }


}