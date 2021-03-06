package com.example.s13firstspring.controllers;


import com.example.s13firstspring.modelsTests.dtos.categories.CategoryAddDTO;
import com.example.s13firstspring.modelsTests.dtos.categories.CategoryResponseDTO;
import com.example.s13firstspring.modelsTests.dtos.categories.CategoryWithSubcategoriesDTO;
import com.example.s13firstspring.modelsTests.entities.Category;
import com.example.s13firstspring.services.CategoryService;
import com.example.s13firstspring.services.utilities.SessionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/categories/add")
    public ResponseEntity<CategoryResponseDTO> add(@RequestBody CategoryAddDTO category, HttpServletRequest request) {
        SessionUtility.isAdmin(request);
        SessionUtility.validateLogin(request);
        return ResponseEntity.ok(categoryService.add(category));
    }

    //-edit discount
    @PutMapping("/categories/edit")
    public ResponseEntity<Category> edit(@RequestBody Category category, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        return ResponseEntity.ok(categoryService.edit(category));
    }

    //-remove discount
    @DeleteMapping("/categories/delete/{id}")
    public void delete(@PathVariable int id, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        SessionUtility.isAdmin(request);
        categoryService.delete(id);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryWithSubcategoriesDTO> getById(@PathVariable int id,HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        return ResponseEntity.ok(categoryService.getById(id));
    }
}
