package com.example.s13firstspring.controllers;


import com.example.s13firstspring.models.entities.Category;
import com.example.s13firstspring.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<Category> add(@RequestBody Category category,HttpSession session, HttpServletRequest request) {
        UserController.validateLogin(session, request);
        Category c = categoryService.add(category);
        return ResponseEntity.ok(c);
    }
    //-edit discount
    @PutMapping("/edit")
    public ResponseEntity<Category> edit(@RequestBody Category category, HttpSession session, HttpServletRequest request) {
        UserController.validateLogin(session, request);
        Category c = categoryService.edit(category);
        return ResponseEntity.ok(c);
    }
    //-remove discount
    @DeleteMapping("/delete")
    public ResponseEntity<Category>  delete(@RequestBody Category category, HttpSession session, HttpServletRequest request){
        UserController.validateLogin(session, request);

        Category c=categoryService.delete(category);
        return ResponseEntity.accepted().body(c);
    }
}
