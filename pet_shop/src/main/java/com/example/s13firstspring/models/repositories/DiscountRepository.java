package com.example.s13firstspring.models.repositories;

import com.example.s13firstspring.models.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {

    Discount findByName(String name);

    @Modifying
    public void deleteByEndOfOfferBefore(LocalDateTime expiryDateTime);


}
