package com.example.s13firstspring.models;

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
    private long id;
    @Column
    private String name;
    @Column
    private double price;
    @Column
    private int unitsInStock;
    @Column
    private int brand;// TODO
    @Column
    private int subCategory;//TODO
    @Column
    private String animal;
    @Column
    private String description;
    @Column
    private double rating;
    @Column
    private String imageURL;
    @Column
    private int discount; //percents

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }


    // Methods for using parameters


}
