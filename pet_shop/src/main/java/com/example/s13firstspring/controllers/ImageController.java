package com.example.s13firstspring.controllers;

import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.models.entities.Image;
import com.example.s13firstspring.models.entities.Product;
import com.example.s13firstspring.models.entities.User;
import com.example.s13firstspring.services.ImageService;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Files;

@RestController
public class ImageController {
    @Autowired
    ImageService service;


    public Image add(String name, Product p) {
        return service.add(name, p);
    }

    @SneakyThrows
    @GetMapping("/images/{imageName}")
    public void download(@PathVariable String imageName, HttpServletResponse response){
        File f=new File("images"+File.separator+imageName);
        if(!f.exists()){
            throw new NotFoundException("Image does not exist");
        }
        Files.copy(f.toPath(),response.getOutputStream());
    }
}
