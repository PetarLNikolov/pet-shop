package com.example.s13firstspring.services;

import com.example.s13firstspring.exceptions.BadRequestException;
import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.models.dtos.ErrorDTO;
import com.example.s13firstspring.models.entities.Discount;
import com.example.s13firstspring.models.entities.User;
import com.example.s13firstspring.models.repositories.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DiscountService {
    @Autowired
    private DiscountRepository discountRepository;

    @Transactional
    public Discount edit(Discount discount) {
        Optional<Discount> opt = discountRepository.findById(discount.getId());
        if (opt.isPresent()) {
            discountRepository.save(discount);
            return discount;
        } else {
            throw new NotFoundException("Discount not found");
        }
    }

    public Discount add(String name, int percentDiscount, LocalDateTime startDate, LocalDateTime endDate) {
        if(discountRepository.findByName(name) != null){
            throw new BadRequestException("Discount name is taken");
        }
        if(startDate.isBefore(LocalDateTime.now())||startDate.isAfter(endDate)){ // subject to change
            throw new BadRequestException("Discount dates are invalid");
        }
        if(percentDiscount<=0||percentDiscount>=100){
            throw new BadRequestException("Discount percentage is invalid");
        }
        Discount d = new Discount();
        d.setName(name);
        d.setStartOfOffer(startDate);
        d.setEndOfOffer(endDate);
        discountRepository.save(d);
        return d;
    }

    public Discount delete(long id) {
        Optional<Discount> opt = discountRepository.findById(id);
        if (opt.isPresent()) {
             discountRepository.deleteById(id);
             return opt.get();
        } else {
            throw new NotFoundException("Discount not found");
        }
    }
}
