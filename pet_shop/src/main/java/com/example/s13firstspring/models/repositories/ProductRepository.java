package com.example.s13firstspring.models.repositories;

import com.example.s13firstspring.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

    Product findByName(String name);
}
