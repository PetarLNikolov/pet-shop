package com.example.s13firstspring.modelsTests.repositories;

import com.example.s13firstspring.modelsTests.entities.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Set<Product> findAllByNameContaining(String name);

    Product findByName(String name);

    List<Product> findAllByOrderByPriceDesc();

    @Query(nativeQuery = true, value="SELECT p.* FROM products AS p ORDER BY p.rating DESC LIMIT 10")
    Set<Product> getTop10Products();
}
