package com.example.s13firstspring.controllers;

import com.example.s13firstspring.models.entities.Image;
import com.example.s13firstspring.models.entities.User;
import com.example.s13firstspring.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {
    @Autowired
    ImageService service;

    public Image add(String name, int id) {
        return service.add(name,id);
    }
}
