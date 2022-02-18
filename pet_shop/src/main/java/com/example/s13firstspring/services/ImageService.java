package com.example.s13firstspring.services;


import com.example.s13firstspring.models.entities.Image;
import com.example.s13firstspring.models.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    @Autowired
    ImageRepository repository;


    public Image add(String name, int id) {
        Image i= new Image();
        i.setImage_URL(name);
        i.setProductID(id);
        return repository.save(i);

    }
}
