package com.example.firstproject.repository;

import com.example.firstproject.entity.Phone;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface PhoneRepository extends CrudRepository<Phone, Long> {

    @Override
    ArrayList<Phone> findAll();
}
