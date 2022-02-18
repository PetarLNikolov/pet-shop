package com.example.s13firstspring.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String image_URL;
    @Column
    private int productID;


    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", image_URL='" + image_URL + '\'' +
                ", productID=" + productID +
                '}';
    }
}
