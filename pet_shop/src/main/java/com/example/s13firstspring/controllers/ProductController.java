package com.example.s13firstspring.controllers;


import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.models.entities.Product;
import com.example.s13firstspring.models.dtos.ProductAddDTO;
import com.example.s13firstspring.models.dtos.ProductEditUnitsDTO;
import com.example.s13firstspring.models.dtos.ProductResponseDTO;
import com.example.s13firstspring.models.repositories.UserRepository;
import com.example.s13firstspring.services.ProductService;
import com.example.s13firstspring.services.utilities.LoginUtility;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService service;
    @Autowired
    private UserRepository userRepository;


    @PostMapping("/products/add")
    public ResponseEntity<ProductResponseDTO> addProduct(@RequestBody ProductAddDTO product, HttpServletRequest request) {
        LoginUtility.validateLogin(request);
        LoginUtility.isAdmin(request);
        return ResponseEntity.ok(service.add(product));
    }

    @GetMapping("/products")
    public ResponseEntity<ProductResponseDTO> get(@RequestParam(name = "name") String name, HttpServletRequest request) {
        LoginUtility.validateLogin(request);

        return ResponseEntity.ok(service.get(name));
    }

    @PutMapping("/products/editUnits/{id}")
    public ResponseEntity<ProductResponseDTO> editUnits(@PathVariable int id, @RequestBody ProductEditUnitsDTO product, HttpServletRequest request) {
        LoginUtility.validateLogin(request);
        LoginUtility.isAdmin(request);
        return ResponseEntity.ok(service.changeInStock(id,product.getUnitsInStock()));
    }

    @DeleteMapping("products/delete/{id}")
    public void delete(@PathVariable int id, HttpServletRequest request) {
        LoginUtility.validateLogin(request);
        LoginUtility.isAdmin(request);
        service.delete(id);
    }
    @PutMapping("/products/edit/{id}")
    public ResponseEntity<ProductResponseDTO> edit(@PathVariable int id, @RequestBody Product product, HttpServletRequest request) {
        LoginUtility.validateLogin(request);
        LoginUtility.isAdmin(request);
        return ResponseEntity.ok(service.edit(product));
    }

    @SneakyThrows
    @PostMapping("/products/addImage/{productID}")
    public String addImage(@RequestParam(name = "file") MultipartFile file, HttpServletRequest request,@PathVariable int productID){
        LoginUtility.validateLogin(request);
        return service.uploadFile(file, request,productID);
    }

    @GetMapping("products/getAllByPrice")
    public List<ProductResponseDTO> getAllByPrice(HttpServletRequest request){
        LoginUtility.validateLogin(request);
        return service.getAllByPrice();
    }

    @PostMapping("/products/addToFavourite/{id}")
    public int addToFavourite(@PathVariable int id,HttpServletRequest request){
        LoginUtility.validateLogin(request);
        int userId= (int) request.getSession().getAttribute(LoginUtility.USER_ID);
        return service.addToFavourites(id,userId);
    }
}
