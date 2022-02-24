package com.example.s13firstspring.services;

import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.models.dtos.*;
import com.example.s13firstspring.models.entities.Delivery;
import com.example.s13firstspring.models.repositories.DeliveryRepository;
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


    @Transactional
    public DeliveryResponseDTO edit(DeliveryEditDTO delivery, int id) {
        Delivery d = deliveryRepository.findById(id).orElseThrow(() -> new NotFoundException("Delivery not found"));
        d = mapper.map(delivery, Delivery.class);
        d.setId(id);
        deliveryRepository.save(d);
        return mapper.map(d, DeliveryResponseDTO.class);
    }

    public void deleteDelivery(int id) {
        deliveryRepository.delete(deliveryRepository.findById(id).orElseThrow(() -> new NotFoundException("Delivery not found")));
        //If needed orders can be deleted or safe deleted!
    }

    @Transactional
    public String sendDelivery(int id, HttpServletRequest request) {
        Delivery d = deliveryRepository.findById(id).orElseThrow(() -> new NotFoundException("Delivery not found"));
        //If needed orders can be deleted or safe deleted!
        deleteDelivery(id);
        String response="Your order is complete!"+" The totalAmount is:"+ d.getTotalCost();
        return response;
    }

    public DeliveryResponseDTO getById(int id) {
        Delivery d = deliveryRepository.findById(id).orElseThrow(() -> new NotFoundException("Delivery not found"));
        DeliveryResponseDTO dto = mapper.map(d, DeliveryResponseDTO.class);
        return dto;

    }
}
