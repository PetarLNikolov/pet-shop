package com.example.s13firstspring.services;


import com.example.s13firstspring.exceptions.BadRequestException;
import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.models.dtos.DiscountAddDTO;
import com.example.s13firstspring.models.dtos.DiscountResponseDTO;
import com.example.s13firstspring.models.entities.Discount;
import com.example.s13firstspring.models.repositories.DiscountRepository;
import com.example.s13firstspring.models.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.Queue;

@Service
public class DiscountService {
    @Autowired
    private DiscountRepository repository;
    @Autowired
    ModelMapper mapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private JavaMailSender emailSender;

    @Transactional
    public Discount edit(DiscountAddDTO discount, int id) {
        Discount d = repository.findById(id).orElseThrow(() -> new NotFoundException("Discount not found"));
        validation(discount);
        //TODO OPRAVI SI DISCOUNT DATE--- UNSUPORTED MEDIA TYPE
        d = repository.save(mapper.map(discount, Discount.class));

        notifyUsers(d);
        return d;
    }

    private void notifyUsers(Discount discount) {
        int discount_id = discount.getId();
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT u.email AS emails,p.name AS product_name FROM users AS u INNER JOIN users_have_favourites AS uhf ON u.id=uhf.user_id INNER JOIN products AS p ON p.id=uhf.product_id INNER JOIN discounts AS d ON d.id=p.discount_id WHERE d.id=(?)", discount_id);
        Queue<String> emails = new LinkedList<>();
        new Thread(() -> {
            while (rowSet.next()) {
                String likerEmail = rowSet.getString("emails");
                String productName = rowSet.getString("product_name");
                String text = "Great news, a product you like has been discounted. " + productName + " is now " + discount.getPercentDiscount() + " % OFF!";
                sendSimpleMessage(likerEmail, "New discount for " + productName, text);
            }
        }).start();
    }


    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("peshou2@gmail.com"); //TODO add as constant
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }


    public DiscountResponseDTO add(DiscountAddDTO discount) {
        validation(discount);
        Discount d = new Discount();
        d.setName(discount.getName());
        d.setStartOfOffer(discount.getStartOfOffer());
        d.setEndOfOffer(discount.getEndOfOffer());
        d.setPercentDiscount(discount.getPercentDiscount());
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
        if (discount.getStartOfOffer().isAfter(discount.getEndOfOffer())) { // subject to change
            throw new BadRequestException("Discount dates are invalid");
        }
        if (discount.getPercentDiscount() <= 0 || discount.getPercentDiscount() >= 100) {
            throw new BadRequestException("Discount percentage is invalid");
        }
    }
}
