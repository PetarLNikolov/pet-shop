package com.example.s13firstspring.models.entities;

import com.example.s13firstspring.models.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "brands")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String brandName;

    @Column
    private String imageURL;

    @OneToMany(mappedBy = "brand")
    Set<Product> products;

}
