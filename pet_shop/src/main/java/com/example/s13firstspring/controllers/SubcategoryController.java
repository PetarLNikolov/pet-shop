package com.example.s13firstspring.controllers;


import com.example.s13firstspring.models.entities.Subcategory;
import com.example.s13firstspring.models.repositories.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // controller + request body(for serialization to json of the HTTP response to the request)
@RequestMapping("/subcategories")
public class SubcategoryController {

    @Autowired
    private SubcategoryRepository repo;

    @PostMapping("/users")
    public Subcategory add(@RequestBody Subcategory u) {
        repo.save(u);
        return u;
    }
}
