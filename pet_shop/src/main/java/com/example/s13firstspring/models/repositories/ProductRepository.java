package com.example.s13firstspring.models.repositories;

import com.example.s13firstspring.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<com.example.s13firstspring.models.Product, Integer> {

    com.example.s13firstspring.models.Product findByName(String name);
}
