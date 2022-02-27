package com.example.s13firstspring.services;


import com.example.s13firstspring.exceptions.BadRequestException;
import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.models.dtos.discounts.DiscountAddDTO;
import com.example.s13firstspring.models.dtos.discounts.DiscountResponseDTO;
import com.example.s13firstspring.models.entities.Discount;
import com.example.s13firstspring.models.entities.Product;
import com.example.s13firstspring.models.repositories.DiscountRepository;
import com.example.s13firstspring.models.repositories.UserRepository;
import com.sun.xml.bind.v2.TODO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @Autowired
    EntityManager entityManager;

    @Transactional
    public Discount edit(DiscountAddDTO discount, int id) {

        Discount d = repository.findById(id).orElseThrow(() -> new NotFoundException("Discount not found"));
        if (discount.getStartOfOffer().isAfter(discount.getEndOfOffer())) {
            throw new BadRequestException("Discount dates are invalid");
        }
        if (discount.getPercentDiscount() <= 0 || discount.getPercentDiscount() >= 100) {
            throw new BadRequestException("Discount percentage is invalid");
        }
        updateProductDiscountedPrice(id);
//        //TODO update product discounted price
//        TypedQuery<Product> productQuery =
//                entityManager.createQuery("SELECT p FROM Product p  WHERE p.discount.id= (:arg1)", Product.class);
//        List<Product> allProducts = productQuery.setParameter("arg1", id).getResultList();
//        for (Product product : allProducts) {
//            product.setDiscountPrice(product.getPrice() * (100 - product.getDiscount().getPercentDiscount()) / 100);
//        }

        d = mapper.map(discount, Discount.class);
        d.setId(id);
        repository.save(d);
        notifyUsers(d);
        return d;
    }
    private void updateProductDiscountedPrice(int id){
        TypedQuery<Product> productQuery =
                entityManager.createQuery("SELECT p FROM Product p  WHERE p.discount.id= (:arg1)", Product.class);
        List<Product> allProducts = productQuery.setParameter("arg1", id).getResultList();
        for (Product product : allProducts) {
            product.setDiscountPrice(product.getPrice() * (100 - product.getDiscount().getPercentDiscount()) / 100);
        }
    }
    private void notifyUsers(Discount discount) {
        int discount_id = discount.getId();
        sendSimpleMessage("peshou2@gmail.com","sda","hello discount time");
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT u.email AS emails,p.name AS product_name FROM users AS u INNER JOIN users_have_favourites AS uhf ON u.id=uhf.user_id INNER JOIN products AS p ON p.id=uhf.product_id INNER JOIN discounts AS d ON d.id=p.discount_id WHERE d.id=(?)", discount_id);
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
        message.setFrom("peshou2@gmail.com");
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
        if (discount.getStartOfOffer().isAfter(discount.getEndOfOffer())) {
            throw new BadRequestException("Discount dates are invalid");
        }
        if (discount.getPercentDiscount() <= 0 || discount.getPercentDiscount() >= 100) {
            throw new BadRequestException("Discount percentage is invalid");
        }
    }
}
