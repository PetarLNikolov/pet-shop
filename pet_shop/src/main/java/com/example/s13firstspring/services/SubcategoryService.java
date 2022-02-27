package com.example.s13firstspring.services;

import com.example.s13firstspring.exceptions.BadRequestException;
import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.models.dtos.subcategories.SubcategoryAddDTO;
import com.example.s13firstspring.models.dtos.subcategories.SubcategoryResponseDTO;
import com.example.s13firstspring.models.entities.Subcategory;
import com.example.s13firstspring.models.repositories.CategoryRepository;
import com.example.s13firstspring.models.repositories.SubcategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SubcategoryService {

    @Autowired
    private SubcategoryRepository repository;
    @Autowired
    ModelMapper mapper;
    @Autowired
    CategoryRepository categoryRepository;

    public SubcategoryResponseDTO add(SubcategoryAddDTO subcategory) {
        if (repository.findByName(subcategory.getName()) != null) {
            throw new BadRequestException("Subcategory name already exists" );
        }
        Subcategory s = new Subcategory();
        s.setName(subcategory.getName());
        s.setCategory(categoryRepository.getById(subcategory.getCategory_id()));
        repository.save(s);
        return mapper.map(s, SubcategoryResponseDTO.class);
    }

    @Transactional
    public SubcategoryResponseDTO edit(Subcategory subcategory) {
        repository.findById(subcategory.getId()).orElseThrow(() -> new NotFoundException("Subcategory not found" ));
        repository.save(subcategory);
        return mapper.map(subcategory, SubcategoryResponseDTO.class);
    }

    public void delete(int id) {
        repository.delete(repository.findById(id).orElseThrow(() -> new NotFoundException("Subcategory not found" )));
    }

    public SubcategoryResponseDTO getById(int id) {
        return mapper.map(repository.findById(id).orElseThrow(() -> new NotFoundException("Subcategory not found" )), SubcategoryResponseDTO.class);
    }
}
