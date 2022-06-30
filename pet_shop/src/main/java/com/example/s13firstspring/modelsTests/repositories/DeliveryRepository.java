package com.example.s13firstspring.modelsTests.repositories;

import com.example.s13firstspring.modelsTests.entities.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery,Integer> {

    Delivery getById(int id);

}
