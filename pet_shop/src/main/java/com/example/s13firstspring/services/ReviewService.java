package com.example.s13firstspring.services;

import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.models.dtos.ReviewAddDTO;
import com.example.s13firstspring.models.dtos.ReviewResponseDTO;
import com.example.s13firstspring.models.entities.Review;
import com.example.s13firstspring.models.repositories.ReviewRepository;
import com.example.s13firstspring.models.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository repository;
    @Autowired
    ModelMapper mapper;


    public Review addReview(ReviewAddDTO review) {
        Review r = mapper.map(review, Review.class);
        return repository.save(r);
    }

    public ReviewResponseDTO getByID(int id) {
        return mapper.map(repository.findById(id).orElseThrow(() -> new NotFoundException("Review not found")), ReviewResponseDTO.class);
    }
}
