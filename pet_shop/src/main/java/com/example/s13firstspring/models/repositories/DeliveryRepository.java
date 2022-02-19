package com.example.s13firstspring.models.repositories;

import com.example.s13firstspring.models.entities.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery,Long> {

    Delivery getById(long id);

}
