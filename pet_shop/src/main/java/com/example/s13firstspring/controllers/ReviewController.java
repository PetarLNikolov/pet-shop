package com.example.s13firstspring.controllers;



import com.example.s13firstspring.models.dtos.reviews.ReviewResponseDTO;
import com.example.s13firstspring.services.ReviewService;
import com.example.s13firstspring.services.utilities.SessionUtility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ReviewController {

    @Autowired
    ReviewService service;
    @Autowired
    ModelMapper mapper;



    @GetMapping("/reviews/getByID/{id}")
    public ResponseEntity<ReviewResponseDTO> getByID(@PathVariable int id, HttpServletRequest request){
        SessionUtility.validateLogin(request);
        return ResponseEntity.ok().body(service.getByID(id));
    }

}
