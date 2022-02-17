package com.example.s13firstspring.models.entities;

import com.example.s13firstspring.models.entities.Delivery;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "cities")
@Data
@NoArgsConstructor

public class City {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String city_name;


    @OneToMany(mappedBy = "city")
    private List<Delivery> deliveries;

}
