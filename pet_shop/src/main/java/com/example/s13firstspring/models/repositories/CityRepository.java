package com.example.s13firstspring.models.repositories;

import com.example.s13firstspring.models.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City,Long> {
}
