package com.example.s13firstspring.models.repositories;

import com.example.s13firstspring.models.entities.Order;
import com.example.s13firstspring.models.entities.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findByDeliveries(Delivery deliveries);
}