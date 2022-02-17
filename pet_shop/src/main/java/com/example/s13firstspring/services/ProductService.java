package com.example.s13firstspring.services;


import com.example.s13firstspring.exceptions.BadRequestException;
import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.models.Product;
import com.example.s13firstspring.models.ProductRepository;
import com.example.s13firstspring.models.dtos.ProductAddDTO;
import com.example.s13firstspring.models.dtos.ProductResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;
    @Autowired
    private ModelMapper mapper;


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
        Product p = new Product();
        p.setName(product.getName());
        p.setPrice(product.getPrice());
        p.setUnitsInStock(product.getUnitsInStock());
        //p.setBrand(brandID);
        //p.setAnimal(animal);
        //p.setSubCategory(subcatedoryID);
        p.setDescription(product.getDescription());
        repository.save(p);
        return mapper.map(p, ProductResponseDTO.class);
    }

    public Product getByID(int id) {
        Optional<Product> opt = repository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        } else
            throw new NotFoundException("Product not found");
    }

    @Transactional
    public Product edit(Product product) {
        Optional<Product> opt = repository.findById(product.getId());
        if (opt.isPresent()) {
            repository.save(product);//update users set ..... where id = user.getId
            //commentRepository.deleteAll(user.getComments());
            return product;
        } else {
            throw new NotFoundException("User not found");
        }
    }


    public void delete(int id) {
        if (!repository.findById(id).isPresent()) {
            throw new NotFoundException("Product not found");
        }
        repository.deleteById(id);
    }

    public Product changeInStock(int id, int countOfStock) {
        if (repository.findById(id) == null) {
            throw new NotFoundException("Category not found");
        }
        if (countOfStock < 0) {
            throw new BadRequestException("Stock cant be <= 0");
        }
        repository.findById(id).get().setUnitsInStock(countOfStock);
        return repository.findById(id).get();
    }
}
