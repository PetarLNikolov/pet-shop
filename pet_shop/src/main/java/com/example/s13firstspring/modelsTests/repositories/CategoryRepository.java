package com.example.s13firstspring.modelsTests.repositories;

import com.example.s13firstspring.modelsTests.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findByName(String name);
}
