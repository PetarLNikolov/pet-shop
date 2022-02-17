package com.example.s13firstspring.models.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "deliveries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name", nullable = false, length = 128)
    private String firstName1;

    @Column(name = "last_name", nullable = false, length = 128)
    private String lastName1;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber1;

    @Column(name = "region", nullable = false, length = 64)
    private String region;

    @Column(name = "post_code", nullable = false)
    private Integer postCode1;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(name = "house_number")
    private Integer houseNumber;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "appartament_number")
    private Integer appartamentNumber;

    @Column(name = "needs_bill")
    private Boolean needsBill;

    @Lob
    @Column(name = "extra_infromation")
    private String extraInformation;

    @OneToMany(mappedBy = "delivery")
    private List<Order> orders;

}
