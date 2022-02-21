package com.example.s13firstspring.controllers;


import com.example.s13firstspring.models.dtos.ReviewAddDTO;
import com.example.s13firstspring.models.dtos.ReviewResponseDTO;
import com.example.s13firstspring.models.entities.Review;
import com.example.s13firstspring.services.ReviewService;
import com.example.s13firstspring.services.utilities.LoginUtility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ReviewController {

    @Autowired
    ReviewService service;
    @Autowired
    ModelMapper mapper;

    @PostMapping("/reviews/add")
    public ReviewResponseDTO addReview(@RequestBody ReviewAddDTO review, HttpServletRequest request) {
        LoginUtility.validateLogin(request);
        return service.addReview(review);
    }

    @GetMapping("/reviews/getByID/{id}")
    public ReviewResponseDTO getByID(@PathVariable int id, HttpServletRequest request){
        LoginUtility.validateLogin(request);
        return service.getByID(id);
    }

}
