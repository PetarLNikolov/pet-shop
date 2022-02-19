package com.example.s13firstspring.models.repositories;

import com.example.s13firstspring.models.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends JpaRepository<Animal,Integer> {

    Animal findByName(String name);
}
