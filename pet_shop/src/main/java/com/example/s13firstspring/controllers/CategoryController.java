package com.example.s13firstspring.controllers;


import com.example.s13firstspring.models.dtos.CategoryAddDTO;
import com.example.s13firstspring.models.dtos.CategoryResponseDTO;
import com.example.s13firstspring.models.dtos.CategoryWithSubcategoriesDTO;
import com.example.s13firstspring.models.entities.Category;
import com.example.s13firstspring.services.CategoryService;
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
    public ResponseEntity<CategoryResponseDTO> add(@RequestBody CategoryAddDTO category,HttpSession session, HttpServletRequest request) {
        UserController.validateLogin(session, request);
        return ResponseEntity.ok(categoryService.add(category));
    }

    //-edit discount
    @PutMapping("/categories/edit")
    public ResponseEntity<Category> edit(@RequestBody Category category, HttpSession session, HttpServletRequest request) {
        UserController.validateLogin(session, request);
        return ResponseEntity.ok( categoryService.edit(category));
    }
    //-remove discount
    @DeleteMapping("/categories/delete")
    public ResponseEntity<Category>  delete(@RequestBody Category category, HttpSession session, HttpServletRequest request){
        UserController.validateLogin(session, request);
        return ResponseEntity.accepted().body(categoryService.delete(category));
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryWithSubcategoriesDTO> getById(@PathVariable int id){
        return ResponseEntity.ok(categoryService.getById(id));
    }
}
