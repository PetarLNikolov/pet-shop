package com.example.s13firstspring.controllers;


import com.example.s13firstspring.models.dtos.discounts.DiscountAddDTO;
import com.example.s13firstspring.models.dtos.discounts.DiscountResponseDTO;
import com.example.s13firstspring.models.entities.Discount;

import com.example.s13firstspring.models.entities.Product;
import com.example.s13firstspring.services.DiscountService;
import com.example.s13firstspring.services.utilities.SessionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DiscountController {
    @Autowired
    private DiscountService discountService;


    //-add discount
    @PostMapping("/discounts/add")

    public ResponseEntity<DiscountResponseDTO> add(@RequestBody DiscountAddDTO discount, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        SessionUtility.isAdmin(request);
        return ResponseEntity.ok(discountService.add(discount));
    }

    //-edit discount
    @PutMapping("/discounts/edit/{id}")
    public ResponseEntity<Discount> edit(@PathVariable int id, @RequestBody DiscountAddDTO discount, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        SessionUtility.isAdmin(request);
        return ResponseEntity.ok(discountService.edit(discount, id));
    }

    //-remove discount
    @DeleteMapping("/discounts/delete/{id}")
    public void delete(@PathVariable int id, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        SessionUtility.isAdmin(request);
        discountService.delete(id);
        request.getSession().invalidate();
    }


//    @Transactional
//    @PutMapping("/discounts/test")
//    public void whenUpdatingEntities_thenCreatesBatch() {
//        TypedQuery<Product> productQuery =
//                entityManager.createQuery("SELECT p FROM Product p", Product.class);
//        List<Product> allProducts = productQuery.getResultList();
//        for (Product product : allProducts) {
//            product.setDiscountPrice(10.01);
//        }
//    }
}
