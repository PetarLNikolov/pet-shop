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
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 128)
    private String lastName;

    @Column(name = "phone_number", nullable = false, length = 128)
    private String phoneNumber;

    @Column(name = "email", nullable = false, length = 128)
    private String email;

    @Column(name = "region", length = 64)
    private String region;

    @Column(name = "post_code")
    private int postCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "house_number")
    private int houseNumber;

    @Column(name = "floor")
    private int floor;

    @Column(name = "appartament_number")
    private int appartamentNumber;

    @Column(name = "needs_bill")
    private Boolean needsBill;

    @Column(name = "extra_information")
    private String extraInformation;

    @OneToMany(mappedBy = "delivery")
    private List<Order> orders;

}
