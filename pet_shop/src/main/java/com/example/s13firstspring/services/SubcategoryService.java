package com.example.s13firstspring.services;

import com.example.s13firstspring.exceptions.BadRequestException;
import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.models.dtos.*;
import com.example.s13firstspring.models.entities.Category;
import com.example.s13firstspring.models.entities.Subcategory;
import com.example.s13firstspring.models.repositories.SubcategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubcategoryService {

    @Autowired
    private SubcategoryRepository subcategoryRepository;
    @Autowired
    ModelMapper mapper;


    public Subcategory save(Subcategory u) {
        subcategoryRepository.save(u);
        return u;
    }

    public SubcategoryResponseDTO add(SubcategoryAddDTO subcategory) {
        if(subcategoryRepository.findByName(subcategory.getName()) != null){
            throw new BadRequestException("Subcategory name already exists");
        }
        Subcategory s=new Subcategory();
        s.setName(subcategory.getName());
        subcategoryRepository.save(s);
        return mapper.map(s,SubcategoryResponseDTO.class);
    }

    @Transactional
    public SubcategoryResponseDTO edit(Subcategory subcategory) {
        Optional<Subcategory> opt = subcategoryRepository.findById((long) subcategory.getId());
        if (opt.isPresent()) {
            subcategoryRepository.save(subcategory);
            return mapper.map(subcategory,SubcategoryResponseDTO.class);
        } else {
            throw new NotFoundException("Subcategory not found");
        }
    }

    public void delete(int id) {
        Optional<Subcategory> opt = subcategoryRepository.findById((long) id);
        if(!opt.isPresent()){
            throw new NotFoundException("Category not found");
        }
        subcategoryRepository.deleteById((long) id);

    }

    public SubcategoryResponseDTO getById(int id) {
        Optional<Subcategory> opt = subcategoryRepository.findById((long) id);
        if(!opt.isPresent()) {
            throw new NotFoundException("Subcategory not found");
        }
        return mapper.map(opt,SubcategoryResponseDTO.class);
    }
}
