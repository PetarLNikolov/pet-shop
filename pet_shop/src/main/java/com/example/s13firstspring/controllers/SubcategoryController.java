package com.example.s13firstspring.controllers;


import com.example.s13firstspring.models.dtos.subcategories.SubcategoryAddDTO;
import com.example.s13firstspring.models.dtos.subcategories.SubcategoryResponseDTO;
import com.example.s13firstspring.models.entities.Subcategory;
import com.example.s13firstspring.services.SubcategoryService;
import com.example.s13firstspring.services.utilities.SessionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController  // controller + request body(for serialization to json of the HTTP response to the request)

public class SubcategoryController {

    @Autowired
    private SubcategoryService subcategoryService;



    @PostMapping("/subcategories/add")
    public ResponseEntity<SubcategoryResponseDTO> add(@RequestBody SubcategoryAddDTO subcategory, HttpServletRequest request) {
        SessionUtility.isAdmin(request);
        SessionUtility.validateLogin(request);
        return ResponseEntity.ok(subcategoryService.add(subcategory));
    }


    //-edit discount
    @PutMapping("/subcategories/edit")
    public ResponseEntity<SubcategoryResponseDTO> edit(@RequestBody Subcategory subcategory, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        return ResponseEntity.ok(subcategoryService.edit(subcategory));
    }

    //-remove discount
    @DeleteMapping("/subcategories/delete/{id}")
    public void delete(@PathVariable int id, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        subcategoryService.delete(id);
    }

    @GetMapping("/subcategories/{id}")
    public ResponseEntity<SubcategoryResponseDTO> getById(@PathVariable int id, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        return ResponseEntity.ok(subcategoryService.getById(id));
    }


}
