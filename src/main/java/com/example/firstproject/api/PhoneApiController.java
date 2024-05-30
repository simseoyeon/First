package com.example.firstproject.api;

import com.example.firstproject.entity.Phone;
import com.example.firstproject.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PhoneApiController { //핸드폰 - 브랜드, 제품명, 가격

    @Autowired
    private PhoneRepository phoneRepository;
    //GET
    @GetMapping("/api/phones") //전체 데이터 조회
    public List<Phone> index(){
        return phoneRepository.findAll(); //repository에서 phone의 데이터를 모두 찾아서 반환
    }

    @GetMapping("/api/phones/{id}")
    public Phone show(@PathVariable Long id){ //메서드의 수행결과를 phone 객체에 반환한다.
        return phoneRepository.findById(id).orElse(null);
    }
    //POST
    //PATCH
    //DELETE
}
