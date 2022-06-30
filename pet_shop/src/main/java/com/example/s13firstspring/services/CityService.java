package com.example.s13firstspring.services;

import com.example.s13firstspring.exceptions.BadRequestException;
import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.modelsTests.dtos.cities.CityAddDTO;
import com.example.s13firstspring.modelsTests.dtos.cities.CityWithDeliveriesDTO;
import com.example.s13firstspring.modelsTests.entities.City;
import com.example.s13firstspring.modelsTests.repositories.CityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CityService {
    @Autowired
    CityRepository repository;
    @Autowired
    ModelMapper mapper;

    public City add(CityAddDTO city) {
        if (repository.findByName(city.getCity_name()) != null) {
            throw new BadRequestException("City name already exists");
        }
        City c = mapper.map(city,City.class);
        repository.save(c);
        return c;
    }

    @Transactional
    public City edit(City city) {
        repository.findById(city.getId()).orElseThrow(() -> new NotFoundException("City not found"));
        return repository.save(city);
    }

    public void delete(int id) {
        repository.delete(repository.findById(id).orElseThrow(() -> new NotFoundException("City not found")));
    }

    public CityWithDeliveriesDTO getById(int id) {
        City c = repository.findById(id).orElseThrow(() -> new NotFoundException("City not found"));
        return mapper.map(c,CityWithDeliveriesDTO.class);
    }
}
