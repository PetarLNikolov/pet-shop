package com.example.s13firstspring.services;

import com.example.s13firstspring.exceptions.BadRequestException;
import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.modelsTests.dtos.categories.CategoryAddDTO;
import com.example.s13firstspring.modelsTests.dtos.categories.CategoryResponseDTO;
import com.example.s13firstspring.modelsTests.dtos.categories.CategoryWithSubcategoriesDTO;
import com.example.s13firstspring.modelsTests.dtos.subcategories.SubcategoryWithoutCategoryDTO;
import com.example.s13firstspring.modelsTests.entities.Category;
import com.example.s13firstspring.modelsTests.entities.Subcategory;
import com.example.s13firstspring.modelsTests.repositories.CategoryRepository;
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
    CategoryRepository repository;
    @Autowired
    ModelMapper mapper;


    public CategoryResponseDTO add(CategoryAddDTO category) {
        if (repository.findByName(category.getName()) != null) {
            throw new BadRequestException("Category name already exists");
        }
        Category c = new Category();
        c.setName(category.getName());
        repository.save(c);
        return mapper.map(c, CategoryResponseDTO.class);
    }

    @Transactional
    public Category edit(Category category) {
        repository.findById(category.getId()).orElseThrow(() -> new NotFoundException("Category not found"));
        return repository.save(category);
    }

    public void delete(int id) {
        repository.delete(repository.findById(id).orElseThrow(() -> new NotFoundException("Category not found")));
    }

    public CategoryWithSubcategoriesDTO getById(int id) {
        Optional<Category> opt = repository.findById(id);
        if (opt.isPresent()) {
            Category c = opt.get();
            CategoryWithSubcategoriesDTO dto = mapper.map(c, CategoryWithSubcategoriesDTO.class);
            Set<Subcategory> subcategories = c.getSubcategories();
            dto.setSubcategories(subcategories.stream().map(s -> mapper.map(s, SubcategoryWithoutCategoryDTO.class)).collect(Collectors.toList()));
            return dto;
        } else {
            throw new NotFoundException("Category not found");
        }
    }
}
