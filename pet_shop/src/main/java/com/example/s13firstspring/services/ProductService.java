package com.example.s13firstspring.services;


import com.example.s13firstspring.controllers.ImageController;
import com.example.s13firstspring.exceptions.BadRequestException;
import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.models.entities.Image;
import com.example.s13firstspring.models.entities.Product;
import com.example.s13firstspring.models.entities.User;
import com.example.s13firstspring.models.repositories.ProductRepository;
import com.example.s13firstspring.models.dtos.ProductAddDTO;
import com.example.s13firstspring.models.dtos.ProductResponseDTO;
import com.example.s13firstspring.services.utilities.LoginUtility;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ImageController imageController;


    public ProductResponseDTO add(ProductAddDTO product) {
        if (repository.findByName(product.getName()) != null) {
            throw new BadRequestException("Product name is taken");
        }
        if (product.getPrice() < 0) {
            throw new BadRequestException("Product price is lower than 0");
        }
        if (product.getUnitsInStock() < 0) {
            throw new BadRequestException("Product entity count lower than 0");
        }
        Product p = mapper.map(product, Product.class);
        repository.save(p);
        return mapper.map(p, ProductResponseDTO.class);
    }

    public ProductResponseDTO get(String name) {
        Product product = repository.findByName(name); //TODO findByName- optional so you can call orElse throw exception
        if (product == null) {
            throw new NotFoundException("Product not found");
        }
        return mapper.map(product, ProductResponseDTO.class);
    }

    public void delete(int id) {
        if (!repository.findById(id).isPresent()) {
            throw new NotFoundException("Product not found");
        }
        repository.deleteById(id);
    }

    public ProductResponseDTO changeInStock(int id, int countOfStock) {
        if (countOfStock < 0) {
            throw new BadRequestException("Stock cant be less than 0");
        }
        Product p = repository.findById(id).orElseThrow(() -> new BadRequestException("Product not found"));
        p.setUnitsInStock(countOfStock);
        repository.save(p);
        return mapper.map(p, ProductResponseDTO.class);
    }

    public ProductResponseDTO edit(Product p) {
        Product p1 = repository.findById(p.getId()).orElseThrow(() -> new BadRequestException("Product not found"));
        repository.save(p1);
        return mapper.map(p1, ProductResponseDTO.class);
    }

    @SneakyThrows
    public String uploadFile(MultipartFile file, HttpServletRequest request,int productID) {

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String name = System.nanoTime() + "." + extension;
        Files.copy(file.getInputStream(), new File("images" + File.separator + name).toPath());
        Product p= repository.getById(productID);
        Image i = imageController.add(name, p);
        p.getImages().add(i);
        repository.save(p);
        return i.getImageURL();
    }
}
