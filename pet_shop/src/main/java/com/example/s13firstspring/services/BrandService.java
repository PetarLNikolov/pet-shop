package com.example.s13firstspring.services;

import com.example.s13firstspring.exceptions.BadRequestException;
import com.example.s13firstspring.modelsTests.dtos.brands.BrandAddDTO;
import com.example.s13firstspring.modelsTests.dtos.brands.BrandResponseDTO;
import com.example.s13firstspring.modelsTests.entities.Brand;
import com.example.s13firstspring.modelsTests.repositories.BrandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandService {
    @Autowired
    BrandRepository repository;
    @Autowired
    ModelMapper mapper;

    public BrandResponseDTO add(BrandAddDTO brand) {
        validation(brand);
        Brand b=mapper.map(brand,Brand.class);
        repository.save(b);
        return mapper.map(b,BrandResponseDTO.class);
    }

    private void validation(BrandAddDTO brand) {
        if (repository.findByName(brand.getBrandName()) != null) {
            throw new BadRequestException("Brand name is taken");
        }
    }

    public BrandResponseDTO edit(int id) {
        return mapper.map(repository.save(repository.findById(id).
                orElseThrow(() -> new BadRequestException("Brand does not exist"))), BrandResponseDTO.class);
    }
}
