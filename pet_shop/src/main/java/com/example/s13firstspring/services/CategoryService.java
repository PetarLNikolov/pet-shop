package com.example.s13firstspring.services;

import com.example.s13firstspring.exceptions.BadRequestException;
import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.models.entities.Category;
import com.example.s13firstspring.models.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;


    public Category add(Category category) {
        if(categoryRepository.findByName(category.getName()) != null){
            throw new BadRequestException("Category name already exists");
        }
        Category c = new Category();
        categoryRepository.save(c);
        return c;
    }

    @Transactional
    public Category edit(Category category) {
        Optional<Category> opt = categoryRepository.findById(category.getId());
        if (opt.isPresent()) {
            categoryRepository.save(category);
            return category;
        } else {
            throw new NotFoundException("Category not found");
        }
    }

    public Category delete(Category category) {
        if(categoryRepository.findById(category.getId()) == null){
            throw new NotFoundException("Category not found");
        }
        Category c = new Category();
        categoryRepository.delete(c);
        return c;
    }
}
