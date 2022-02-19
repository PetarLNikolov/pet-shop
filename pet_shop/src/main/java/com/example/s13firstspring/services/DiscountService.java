package com.example.s13firstspring.services;

import com.example.s13firstspring.controllers.UserController;
import com.example.s13firstspring.exceptions.BadRequestException;
import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.models.dtos.DiscountAddDTO;
import com.example.s13firstspring.models.dtos.DiscountResponseDTO;
import com.example.s13firstspring.models.entities.Discount;
import com.example.s13firstspring.models.repositories.DiscountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class DiscountService {
    @Autowired
    private DiscountRepository repository;
    @Autowired
    ModelMapper mapper;
    @Autowired
    UserController userController;

    @Transactional
    public Discount edit(Discount discount) {
        repository.findById(discount.getId()).orElseThrow(() -> new NotFoundException("Discount not found"));
        userController.notifyDiscountChange(discount);
        validation(mapper.map(discount,DiscountAddDTO.class));
        return repository.save(discount);
    }

    public DiscountResponseDTO add(DiscountAddDTO discount) {
        validation(discount);
        Discount d = new Discount();
        d.setName(discount.getName());
        d.setStartOfOffer(discount.getStartOfOffer());
        d.setEndOfOffer(discount.getEndOfOffer());
        repository.save(d);
        return mapper.map(d, DiscountResponseDTO.class);
    }

    public void delete(int id) {
        repository.delete(repository.findById(id).orElseThrow(() -> new NotFoundException("Discount not found")));
    }

    public void validation(DiscountAddDTO discount){
        if (repository.findByName(discount.getName()) != null) {
            throw new BadRequestException("Discount name is taken");
        }
        if (discount.getStartOfOffer().isBefore(LocalDateTime.now()) || discount.getStartOfOffer().isAfter(discount.getEndOfOffer())) { // subject to change
            throw new BadRequestException("Discount dates are invalid");
        }
        if (discount.getPercentDiscount() <= 0 || discount.getPercentDiscount() >= 100) {
            throw new BadRequestException("Discount percentage is invalid");
        }
    }
}
