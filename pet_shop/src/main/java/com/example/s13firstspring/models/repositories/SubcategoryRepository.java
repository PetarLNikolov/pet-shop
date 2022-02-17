package com.example.s13firstspring.models.repositories;

import com.example.s13firstspring.models.entities.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
    Subcategory findByName(String name);
}
