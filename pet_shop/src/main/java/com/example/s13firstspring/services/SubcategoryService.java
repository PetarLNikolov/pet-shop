package com.example.s13firstspring.services;

import com.example.s13firstspring.models.entities.Subcategory;
import com.example.s13firstspring.models.repositories.SubcategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class SubcategoryService {

    @Autowired
    private SubcategoryRepository subcategoryRepository;
    @Autowired
    ModelMapper mapper;


    public Subcategory save(Subcategory u) {
        subcategoryRepository.save(u);
        return u;
    }
}
