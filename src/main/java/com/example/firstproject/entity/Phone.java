package com.example.firstproject.entity;

import jakarta.persistence.*;

@Entity
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String brand;
    @Column
    private String name;
    @Column
    private String price;
}
