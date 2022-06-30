package com.example.s13firstspring.services;


import com.example.s13firstspring.modelsTests.entities.Image;
import com.example.s13firstspring.modelsTests.entities.Product;
import com.example.s13firstspring.modelsTests.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    @Autowired
    ImageRepository repository;


    public Image add(String name, Product p) {
        Image i= new Image();
        i.setImage_URL(name);
        i.setProduct(p);
        return repository.save(i);

    }
}
