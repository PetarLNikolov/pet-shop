package com.example.s13firstspring.services;

import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.models.dtos.ReviewAddDTO;
import com.example.s13firstspring.models.dtos.ReviewResponseDTO;
import com.example.s13firstspring.models.entities.Review;
import com.example.s13firstspring.models.repositories.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository repository;
    @Autowired
    ModelMapper mapper;

    public ReviewResponseDTO addReview(ReviewAddDTO review) {
        return mapper.map(repository.save(mapper.map(review, Review.class)), ReviewResponseDTO.class);
    }

    public ReviewResponseDTO getByID(int id) {
        return mapper.map(repository.findById(id).orElseThrow(()->new NotFoundException("Review not found")),ReviewResponseDTO.class);
    }
}
