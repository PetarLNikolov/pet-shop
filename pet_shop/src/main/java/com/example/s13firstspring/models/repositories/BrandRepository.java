package com.example.s13firstspring.models.repositories;

import com.example.s13firstspring.models.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Integer> {
    Brand findByName(String name);
}
