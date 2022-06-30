package com.example.s13firstspring.services;

import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.modelsTests.dtos.deliveries.DeliveryEditDTO;
import com.example.s13firstspring.modelsTests.dtos.deliveries.DeliveryResponseDTO;
import com.example.s13firstspring.modelsTests.entities.Delivery;
import com.example.s13firstspring.modelsTests.repositories.CityRepository;
import com.example.s13firstspring.modelsTests.repositories.DeliveryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;


@Service
public class DeliveryService {

    @Autowired
    ModelMapper mapper;

    @Autowired
    private DeliveryRepository deliveryRepository;


    @Autowired
    private CityRepository cityRepository;

    @Transactional
    public DeliveryResponseDTO edit(DeliveryEditDTO delivery, int id) {
        Delivery d = deliveryRepository.findById(id).orElseThrow(() -> new NotFoundException("Delivery not found"));
        Double cost=d.getTotalCost();
        d = mapper.map(delivery, Delivery.class);
        d.setId(id);
        d.setTotalCost(cost);
        d.setCity(cityRepository.getById(delivery.getCityId()));

        return mapper.map( deliveryRepository.save(d), DeliveryResponseDTO.class);
    }



    @Transactional
    public DeliveryResponseDTO sendDelivery(int id, HttpServletRequest request) {
        Delivery d = deliveryRepository.findById(id).orElseThrow(() -> new NotFoundException("Delivery not found"));
        //If needed orders can be deleted or safe deleted!
        return mapper.map(d, DeliveryResponseDTO.class);
    }

    public DeliveryResponseDTO getById(int id) {
        Delivery d = deliveryRepository.findById(id).orElseThrow(() -> new NotFoundException("Delivery not found"));
        DeliveryResponseDTO dto = mapper.map(d, DeliveryResponseDTO.class);
        return dto;

    }
}
