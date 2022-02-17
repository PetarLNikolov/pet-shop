package com.example.s13firstspring.controllers;


import com.example.s13firstspring.models.dtos.CategoryAddDTO;
import com.example.s13firstspring.models.dtos.CategoryResponseDTO;
import com.example.s13firstspring.models.dtos.CategoryWithSubcategoriesDTO;
import com.example.s13firstspring.models.entities.Category;
import com.example.s13firstspring.services.CategoryService;
import com.example.s13firstspring.services.utilities.LoginUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/categories/add")
    public ResponseEntity<CategoryResponseDTO> add(@RequestBody CategoryAddDTO category, HttpServletRequest request) {
        LoginUtility.isAdmin(request);
        LoginUtility.validateLogin(request);
        return ResponseEntity.ok(categoryService.add(category));
    }

    //-edit discount
    @PutMapping("/categories/edit")
    public ResponseEntity<Category> edit(@RequestBody Category category, HttpServletRequest request) {
        LoginUtility.validateLogin(request);
        return ResponseEntity.ok(categoryService.edit(category));
    }

    //-remove discount
    @DeleteMapping("/categories/delete/{id}")
    public void delete(@PathVariable int id, HttpServletRequest request) {
        LoginUtility.validateLogin(request);
        categoryService.delete(id);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryWithSubcategoriesDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }
}
