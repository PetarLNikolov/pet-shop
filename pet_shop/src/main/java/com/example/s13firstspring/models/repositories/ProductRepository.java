package com.example.s13firstspring.models.repositories;

import com.example.s13firstspring.models.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findByName(String name);
}
