package com.example.s13firstspring.services;


import com.example.s13firstspring.models.entities.Image;
import com.example.s13firstspring.models.entities.Product;
import com.example.s13firstspring.models.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    @Autowired
    ImageRepository repository;


    public Image add(String name, Product p) {
        Image i= new Image();
        i.setImageURL(name);
        i.setProduct(p);
        return repository.save(i);

    }
}
