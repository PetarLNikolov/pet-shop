package com.example.s13firstspring.controllers;


import com.example.s13firstspring.models.entities.Product;
import com.example.s13firstspring.models.dtos.ProductAddDTO;
import com.example.s13firstspring.models.dtos.ProductEditUnitsDTO;
import com.example.s13firstspring.models.dtos.ProductResponseDTO;
import com.example.s13firstspring.models.repositories.UserRepository;
import com.example.s13firstspring.services.ProductService;
import com.example.s13firstspring.services.utilities.SessionUtility;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@RestController
public class ProductController {

    @Autowired
    private ProductService service;
    @Autowired
    private UserRepository userRepository;


    @PostMapping("/products/add")
    public ResponseEntity<ProductResponseDTO> addProduct(@RequestBody ProductAddDTO product, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        SessionUtility.isAdmin(request);
        return ResponseEntity.ok(service.add(product));
    }

    @GetMapping("/products/{name}")
    public ResponseEntity<Set<ProductResponseDTO>> searchByName(@PathVariable String name, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        return ResponseEntity.ok(service.getByName(name));
    }

    @GetMapping("/products/getById/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable int id, HttpServletRequest request){
        SessionUtility.validateLogin(request);
        return ResponseEntity.ok(service.findProductById(id));
    }

    @PutMapping("/products/addUnits")
    public ResponseEntity<String> addUnits(@RequestBody ProductEditUnitsDTO product, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        SessionUtility.isAdmin(request);
        return ResponseEntity.ok("Units in stock: "+service.addToStock(product.getId(),product.getNumberOfProducts()));
    }
    @PutMapping("/products/removeUnits")
    public ResponseEntity<String> removeUnits(@RequestBody ProductEditUnitsDTO product, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        SessionUtility.isAdmin(request);
        return ResponseEntity.ok("Units in stock:"+ service.removeFromStock(product.getId(),product.getNumberOfProducts()));
    }

    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        SessionUtility.isAdmin(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Deleted product: "+service.delete(id));
    }
    @PutMapping("/products/edit/{id}")//TODO change product to productAddDTo- request body
    public ResponseEntity<ProductResponseDTO> edit(@PathVariable int id, @RequestBody Product product, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        SessionUtility.isAdmin(request);
        return ResponseEntity.ok(service.edit(product));
    }

    @SneakyThrows
    @PostMapping("/products/uploadImage/{productID}")
    public String uploadImage(@RequestParam(name = "file") MultipartFile file, HttpServletRequest request,@PathVariable int productID){
        SessionUtility.validateLogin(request);
        return service.uploadImage(file, request,productID);
    }

    @GetMapping("/products/getAllByPrice")
    public List<ProductResponseDTO> getAllByPrice(HttpServletRequest request){
        SessionUtility.validateLogin(request);
        return service.getAllByPrice();
    }

    @PostMapping("/products/addToFavourite/{id}")
    public int addToFavourite(@PathVariable int id,HttpServletRequest request){
        SessionUtility.validateLogin(request);
        int userId= (int) request.getSession().getAttribute(SessionUtility.USER_ID);
        return service.addToFavourites(id,userId);
    }

    @PutMapping("/products/setDiscount/{productId}/discount/{discountId}")
    public ResponseEntity<ProductResponseDTO> setDiscount(@PathVariable int productId,@PathVariable int discountId,HttpServletRequest request){
        SessionUtility.validateLogin(request);
        SessionUtility.isAdmin(request);
        return ResponseEntity.ok().body(service.setDiscount(productId,discountId));
    }
}
