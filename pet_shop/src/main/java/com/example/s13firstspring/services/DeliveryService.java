package com.example.s13firstspring.services;

import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.models.dtos.*;
import com.example.s13firstspring.models.entities.Delivery;
import com.example.s13firstspring.models.repositories.DeliveryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;




@Service
public class DeliveryService {

    @Autowired
    ModelMapper mapper;

    @Autowired
    private DeliveryRepository deliveryRepository;

    public DeliveryWithoutCityDTO add(DeliveryWithoutCityDTO delivery) {

        Delivery d =new Delivery();
        d.setExtraInformation(delivery.getExtraInfromation());
        return mapper.map(d, DeliveryWithoutCityDTO.class);
    }

    @Transactional
    public Delivery edit(Delivery delivery) {
        Optional<Delivery> opt = deliveryRepository.findById((long) delivery.getId());
        if (opt.isPresent()) {
            deliveryRepository.save(delivery);
            return delivery;
        } else {
            throw new NotFoundException("Delivery not found");
        }
    }

    public void delete(int id) {
        if (deliveryRepository.getById((long) id) == null) {
            throw new NotFoundException("Delivery not found");
        }
        deliveryRepository.deleteById((long) id);
    }

    public DeliveryWithoutCityDTO getById(int id) {
        Optional<Delivery> opt = deliveryRepository.findById((long) id);
        if (opt.isPresent()) {
            Delivery d = opt.get();
            DeliveryWithoutCityDTO dto = mapper.map(d, DeliveryWithoutCityDTO.class);
            return dto;
        } else {
            throw new NotFoundException("Delivery not found");
        }
    }
}
