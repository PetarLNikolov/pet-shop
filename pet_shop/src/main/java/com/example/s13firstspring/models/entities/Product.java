package com.example.s13firstspring.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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
    private double discountPrice;
    @Column
    private int unitsInStock;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id")
    private Animal animal;
    @Column
    private String description;
    @Column
    private Double rating;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id")
    private Discount discount;
    @OneToMany(mappedBy = "product")
    private Set<Image> images;
    //
    @ManyToMany
    @JoinTable(
            name = "users_have_favourites",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> fans;
    @OneToMany(mappedBy = "product")
    private Set<Review> reviews;

    @OneToMany(mappedBy = "product")
    Set<OrdersHaveProducts> haveProducts=new HashSet<>();

}
