package com.example.s13firstspring.modelsTests.repositories;

import com.example.s13firstspring.modelsTests.entities.OrdersHaveProducts;
import com.example.s13firstspring.modelsTests.entities.OrdersHaveProductsKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersHaveProductsRepository extends JpaRepository<OrdersHaveProducts, OrdersHaveProductsKey> {

}
