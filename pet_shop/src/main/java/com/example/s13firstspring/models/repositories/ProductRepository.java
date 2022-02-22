package com.example.s13firstspring.models.repositories;

import com.example.s13firstspring.models.entities.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findByNameContaining(String name);
    Product findByName(String name);
    List<Product> findAllByOrderByPriceDesc();
}
