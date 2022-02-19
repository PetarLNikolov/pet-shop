package com.example.s13firstspring.services;

import com.example.s13firstspring.exceptions.BadRequestException;
import com.example.s13firstspring.models.dtos.BrandAddDTO;
import com.example.s13firstspring.models.dtos.BrandResponseDTO;
import com.example.s13firstspring.models.repositories.BrandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BrandService {
    @Autowired
    BrandRepository repository;
    @Autowired
    ModelMapper mapper;

    public BrandResponseDTO save(BrandAddDTO brand) {
        validation(brand);
        return new BrandResponseDTO();
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
