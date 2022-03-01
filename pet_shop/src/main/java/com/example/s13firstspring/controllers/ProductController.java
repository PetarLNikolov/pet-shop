package com.example.s13firstspring.controllers;


import com.example.s13firstspring.models.dtos.*;
import com.example.s13firstspring.models.dtos.products.*;
import com.example.s13firstspring.models.dtos.reviews.ReviewResponseDTO;
import com.example.s13firstspring.models.entities.Product;
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
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable int id, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        return ResponseEntity.ok(service.findProductById(id));
    }

    @PutMapping("/products/addUnits")
    public ResponseEntity<ProductWithUnitsAndName> addUnits(@RequestBody ProductEditUnitsDTO product, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        SessionUtility.isAdmin(request);
        return ResponseEntity.ok(service.addToStock(product.getId(), product.getNumberOfProducts()));
    }

    @PutMapping("/products/removeUnits")
    public ResponseEntity<ProductWithUnitsAndName> removeUnits(@RequestBody ProductEditUnitsDTO product, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        SessionUtility.isAdmin(request);
        return ResponseEntity.ok(service.removeFromStock(product.getId(), product.getNumberOfProducts()));
    }

    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        SessionUtility.isAdmin(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Deleted product: " + service.delete(id));
    }

    @PutMapping("/products/edit/{id}")
    public ResponseEntity<ProductResponseDTO> edit(@PathVariable int id, @RequestBody Product product, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        SessionUtility.isAdmin(request);
        return ResponseEntity.ok(service.edit(product,id));
    }

    @SneakyThrows
    @PostMapping("/products/uploadImage/{productID}")
    public ResponseEntity<ImageWithoutProductDTO> uploadImage(@RequestParam(name = "file") MultipartFile file, HttpServletRequest request, @PathVariable int productID) {
        SessionUtility.validateLogin(request);
        return ResponseEntity.ok().body(service.uploadImage(file, productID));
    }

    @GetMapping("/products/getAllByPrice")
    public ResponseEntity<List<ProductResponseDTO>> getAllByPrice(HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        return ResponseEntity.ok().body(service.getAllByPrice());
    }

    @PostMapping("/products/addToFavourite/{id}")
    public ResponseEntity<ProductResponseAddToFavouritesDTO> addToFavourite(@PathVariable int id, HttpServletRequest request) { //TODO make it return Responseentity<dto>
        SessionUtility.validateLogin(request);
        return ResponseEntity.ok().body(service.addToFavourites(id, request));
    }

    @PutMapping("/products/setDiscount/{productId}/discount/{discountId}")
    public ResponseEntity<ProductResponseDTO> setDiscount(@PathVariable int productId, @PathVariable int discountId, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        SessionUtility.isAdmin(request);
        return ResponseEntity.ok().body(service.setDiscount(productId, discountId));
    }

    @GetMapping("/products/getTop10") //TODO use pagination
    public ResponseEntity<Set<ProductResponseDTO>> getTop10(HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        return ResponseEntity.ok().body(service.getTop10());
    }

    @PostMapping("/products/{id}/review/{rating}")
    public ResponseEntity<ReviewResponseDTO> addReview(@PathVariable int id, @PathVariable int rating, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        return ResponseEntity.ok().body(service.addReview((Integer) request.getSession().getAttribute(SessionUtility.USER_ID), id, rating));
    }
}
