package com.example.s13firstspring.services;


import com.example.s13firstspring.exceptions.BadRequestException;
import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.models.dtos.DiscountAddDTO;
import com.example.s13firstspring.models.dtos.DiscountResponseDTO;
import com.example.s13firstspring.models.entities.Discount;
import com.example.s13firstspring.models.repositories.DiscountRepository;
import com.example.s13firstspring.models.repositories.UserRepository;
import com.example.s13firstspring.services.utilities.SpringJdbcConfig;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;

@Service
public class DiscountService {
    @Autowired
    private DiscountRepository repository;
    @Autowired
    ModelMapper mapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SpringJdbcConfig jdbcConfig;

    @Transactional
    public Discount edit(Discount discount) {
        repository.findById(discount.getId()).orElseThrow(() -> new NotFoundException("Discount not found"));
        validation(mapper.map(discount, DiscountAddDTO.class));
        //notifyUsers(discount);
        return repository.save(discount);
    }

//    private void notifyUsers(Discount discount) {
//             int discount_id=discount.getId();
//
//             Set<String> emails= this.jdbcTemplate.queryForObject("SELECT u.email FROM users AS u INNER JOIN users_have_favourites AS uhf ON u.id=uhf.user_id INNER JOIN products AS p ON p.id=uhf.product_id INNER JOIN discounts AS d ON d.id=p.discount_id WHERE d.id=(?)", discount_id)
//
//    }

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

    public void validation(DiscountAddDTO discount) {
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
