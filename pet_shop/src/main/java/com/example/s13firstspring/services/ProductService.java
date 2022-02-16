package com.example.s13firstspring.services;

import com.example.s13firstspring.exception.BadRequestException;
import com.example.s13firstspring.exception.NotFoundException;
import com.example.s13firstspring.models.Product;
import com.example.s13firstspring.models.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product getByID(long id){
        Optional<Product> opt = repository.findById(id);
        if(opt.isPresent()){
            return opt.get();
        }
        else
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
    public Product add(String name, double price, int inStock,int brandID, int subcatedoryID, String animal, String descrtiption) {
        if(repository.findByName(name) != null){
            throw new BadRequestException("Product name is taken");
        }
        if(price<0){
            throw new BadRequestException("Product price is lower than 0");
        }
        if(inStock<0){
            throw new BadRequestException("Product entity count lower than 0");
        }

        if(brandID<0){
            throw new BadRequestException("Product brand ID is lower than 0");
        }
        if(subcatedoryID<0){
            throw new BadRequestException("Product subcategory lower than 0");
        }
        if(animal.trim().length()<1){
            throw new BadRequestException("Product info for which animal not provided");
        }
        Product p = new Product();
        p.setName(name);
        p.setPrice(price);
        p.setUnitsInStock(inStock);
        p.setBrand(brandID);
        p.setAnimal(animal);
        p.setSubCategory(subcatedoryID);
        p.setDescription(descrtiption);
        repository.save(p);
        return p;
    }
    public Product delete(Product product) {
        if(repository.findById(product.getId()) == null){
            throw new NotFoundException("Category not found");
        }
        Product product1 = new Product();
        repository.delete(product1);
        return product1;
    }
    public Product changeInStock(long id, int countOfStock){
        if(repository.findById(id) == null){
            throw new NotFoundException("Category not found");
        }
        if(countOfStock<0){
            throw new BadRequestException("Stock cant be <= 0");
        }
        repository.findById(id).get().setUnitsInStock(countOfStock);
        return repository.findById(id).get();
    }
}
