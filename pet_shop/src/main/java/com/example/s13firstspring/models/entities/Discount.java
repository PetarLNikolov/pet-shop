package com.example.s13firstspring.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "discounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private int percentDiscount;
    @Column
    private LocalDateTime startOfOffer;
    @Column
    private LocalDateTime endOfOffer;
    // foreign key set to products


    @Override
    public String toString() {
        return "Discount{" +
                "id=" + id +
                ", percentDiscount=" + percentDiscount +
                ", startOfOffer=" + startOfOffer +
                ", endOfOffer=" + endOfOffer +
                ", name='" + name + '\'' +
                '}';
    }
}