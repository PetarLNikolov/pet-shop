package com.example.s13firstspring.controllers;

import com.example.s13firstspring.models.entities.Product;
import com.example.s13firstspring.models.repositories.ProductRepository;
import com.example.s13firstspring.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;



    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {
        productRepository.save(product);
        return product;
    }
    //-remove product
    @DeleteMapping("/delete")
    public ResponseEntity<Product> delete(@RequestBody Product product, HttpSession session, HttpServletRequest request){
//        UserController.validateLogin(session, request);

        Product p=productService.delete(product);
        return ResponseEntity.accepted().body(product);
    }
}
