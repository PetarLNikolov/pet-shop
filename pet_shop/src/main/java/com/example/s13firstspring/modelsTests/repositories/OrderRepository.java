package com.example.s13firstspring.modelsTests.repositories;

import com.example.s13firstspring.modelsTests.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {


}