package com.example.s13firstspring.services;

import com.example.s13firstspring.exceptions.BadRequestException;
import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.models.CityRepository;
import com.example.s13firstspring.models.dtos.*;
import com.example.s13firstspring.models.entities.Category;
import com.example.s13firstspring.models.entities.City;
import com.example.s13firstspring.models.entities.Delivery;
import com.example.s13firstspring.models.entities.Subcategory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CityService {
    @Autowired
    CityRepository cityRepository;
    @Autowired
    ModelMapper mapper;
    public City add(CityAddDTO city) {
        if(cityRepository.findByName(city.getCity_name()) != null){
            throw new BadRequestException("City name already exists");
        }
        City c = new City();
        c.setCity_name(city.getCity_name());
        cityRepository.save(c);
        return mapper.map(c, City.class);
    }
    @Transactional
    public City edit(City city) {
        Optional<City> opt = cityRepository.findById(city.getId());
        if (opt.isPresent()) {
            cityRepository.save(city);
            return city;
        } else {
            throw new NotFoundException("City not found");
        }
    }

    public CityReturnDTO delete(City city) {
        if(cityRepository.findById(city.getId()) == null){
            throw new NotFoundException("City not found");
        }
        CityReturnDTO c = mapper.map(city, CityReturnDTO.class);
        cityRepository.delete(city);
        return c;
    }

    public CityWithDeliveriesDTO getById(int id) {
        Optional<City> opt = cityRepository.findById((long) id);
        if(opt.isPresent()){
            City c = opt.get();
            CityWithDeliveriesDTO dto = mapper.map(c, CityWithDeliveriesDTO.class);
            List<Delivery> deliveries = c.getDeliveries();
            dto.setDeliveries(deliveries.stream().map(s -> mapper.map(s, DeliveryWithoutCityDTO.class)).collect(Collectors.toList()));
            return dto;
        }
        else{
            throw new NotFoundException("City not found");
        }
    }
}
