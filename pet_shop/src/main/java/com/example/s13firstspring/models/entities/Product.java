package com.example.s13firstspring.models.entities;

import com.example.s13firstspring.models.entities.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private double price;
    @Column
    private int unitsInStock;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "brand_id")
    private Brand brand;// TODO
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "subcategory_id")
    private Subcategory subcategory;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "animal_id")
    private Animal animal;
    @Column
    private String description;
    @Column
    private double rating;
    @Column
    private String imageURL;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "discount_id")
    private Discount discount; //percents



    // Methods for using parameters


}
