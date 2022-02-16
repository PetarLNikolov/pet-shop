package com.example.s13firstspring.controllers;


import com.example.s13firstspring.models.dtos.SubcategoryWithCategoryDTO;
import com.example.s13firstspring.models.entities.Subcategory;
import com.example.s13firstspring.models.repositories.SubcategoryRepository;
import com.example.s13firstspring.services.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController  // controller + request body(for serialization to json of the HTTP response to the request)
@RequestMapping("/subcategories")
public class SubcategoryController {

    @Autowired
    private SubcategoryService subcategoryService;


    @PostMapping("/subcategories/add")
    public Subcategory add(@RequestBody Subcategory u) {
        //TODO proverki
        return subcategoryService.save(u);
    }


}
