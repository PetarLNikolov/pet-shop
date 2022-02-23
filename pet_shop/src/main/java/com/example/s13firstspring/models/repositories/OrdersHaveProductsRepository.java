package com.example.s13firstspring.models.repositories;

import com.example.s13firstspring.models.entities.OrdersHaveProducts;
import com.example.s13firstspring.models.entities.OrdersHaveProductsKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersHaveProductsRepository extends JpaRepository<OrdersHaveProducts, OrdersHaveProductsKey> {

}
