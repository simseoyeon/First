package com.example.firstproject.dto;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class PhoneDto {
    private Long id;

    private String brand;
    private String name;
    private String price;

//    public Phone toEntity(){
//        return new Phone(id, brand, name, price);
//    }
}
