package com.example.s13firstspring.modelsTests.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cities")
@Data
@NoArgsConstructor

public class City {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;


    @OneToMany(mappedBy = "city")
    private List<Delivery> deliveries;

}
