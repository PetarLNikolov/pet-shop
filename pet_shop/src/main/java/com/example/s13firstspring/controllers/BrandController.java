package com.example.s13firstspring.controllers;

import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.models.dtos.brands.BrandAddDTO;
import com.example.s13firstspring.models.dtos.brands.BrandResponseDTO;
import com.example.s13firstspring.services.BrandService;
import com.example.s13firstspring.services.utilities.SessionUtility;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Files;

@RestController
public class BrandController {

    @Autowired
    private BrandService service;

    @PostMapping("/brands/add")
    public ResponseEntity<BrandResponseDTO> addBrand(@RequestBody BrandAddDTO brand, HttpServletRequest request) {
        SessionUtility.validateLogin(request);
        SessionUtility.isAdmin(request);
        return ResponseEntity.ok(service.add(brand));
    }

    @PutMapping("/brands/edit/{id}")
    public ResponseEntity<BrandResponseDTO> editBrand(@PathVariable int id,HttpServletRequest request){
        SessionUtility.validateLogin(request);
        SessionUtility.isAdmin(request);
        return ResponseEntity.ok(service.edit(id));
    }

    @SneakyThrows
    @GetMapping("/brands/{imageName}")
    public void download(@PathVariable String imageName, HttpServletResponse response){
        File f=new File("images"+File.separator+imageName);
        if(!f.exists()){
            throw new NotFoundException("Image does not exist");
        }
        Files.copy(f.toPath(),response.getOutputStream());
    }
}
