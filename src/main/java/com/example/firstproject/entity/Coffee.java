package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Coffee {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @Column
    private int price;

    public Long getId() {
        return id;
    }

    public void patch(Coffee coffee) {
        if(coffee.name != null)
            this.name = coffee.name;
    }
}
