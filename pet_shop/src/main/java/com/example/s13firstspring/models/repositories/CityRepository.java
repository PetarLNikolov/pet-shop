package com.example.s13firstspring.models.repositories;

import com.example.s13firstspring.models.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City,Long> {
    City findByName(String name);
}
