package com.example.s13firstspring.models.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity(name = "users")
@NoArgsConstructor
@Data
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String username;
    @Column
    private String email;
    @Column
    private String phoneNumber;
    @Column
    private String password;
    @JsonSerialize(using= LocalDateSerializer.class)
    @JsonDeserialize(using= LocalDateDeserializer.class )
    private Date dateOfBirth;
    @Column
    private boolean isAdmin=false; //TODO    (true - false to MySQL 0-1)
    @OneToMany(mappedBy="user")
    private Set<Review> reviews;
    @ManyToMany(mappedBy = "fans")
    Set<Product> favouriteProducts;


}
