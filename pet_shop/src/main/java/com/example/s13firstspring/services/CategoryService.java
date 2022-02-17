package com.example.s13firstspring.services;

import com.example.s13firstspring.exceptions.BadRequestException;
import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.models.dtos.*;
import com.example.s13firstspring.models.entities.Category;
import com.example.s13firstspring.models.entities.Subcategory;
import com.example.s13firstspring.models.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ModelMapper mapper;


    public CategoryResponseDTO add(CategoryAddDTO category) {
        if(categoryRepository.findByName(category.getName()) != null){
            throw new BadRequestException("Category name already exists");
        }
        Category c = new Category();
        c.setName(category.getName());
        categoryRepository.save(c);
        return mapper.map(c,CategoryResponseDTO.class);
    }

    @Transactional
    public Category edit(Category category) {
        Optional<Category> opt = categoryRepository.findById((long) category.getId());
        if (opt.isPresent()) {
            categoryRepository.save(category);
            return category;
        } else {
            throw new NotFoundException("Category not found");
        }
    }

    public Category delete(Category category) {
        if(categoryRepository.findById((long) category.getId()) == null){
            throw new NotFoundException("Category not found");
        }
        Category c = new Category();
        categoryRepository.delete(c);
        return c;
    }

    public CategoryWithSubcategoriesDTO getById(int id) {
        Optional<Category> opt = categoryRepository.findById((long) id);
        if(opt.isPresent()){
            Category c = opt.get();
            CategoryWithSubcategoriesDTO dto = mapper.map(c, CategoryWithSubcategoriesDTO.class);
            Set<Subcategory> subcategories = c.getSubcategories();
            dto.setSubcategories(subcategories.stream().map(s -> mapper.map(s, SubcategoryWithoutCategoryDTO.class)).collect(Collectors.toList()));
            return dto;
        }
        else{
            throw new NotFoundException("Category not found");
        }
    }
}
