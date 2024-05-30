package com.example.firstproject.api;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
public class CoffeeApiController {
    @Autowired
    private CoffeeService coffeeService;

    //GET - 조회
    @GetMapping("/api/coffees")  //coffee의 데이터 전체 조호ㅣ
    public List<Coffee> index(){
        return coffeeService.index();
    }
    //POST - 생성
    @PostMapping("/api/coffees")
    public ResponseEntity<Coffee> create(@RequestBody CoffeeDto dto){
        Coffee saved = coffeeService.save(dto);
        return (saved != null) ?
                ResponseEntity.status(HttpStatus.OK).body(saved):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    //PATCH - 수정
    @PatchMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> update(@PathVariable Long id, @RequestBody CoffeeDto dto){
        Coffee updated = coffeeService.update(id, dto);
        return (updated != null)?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    //DELETE - 삭제
    @DeleteMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> delete(@PathVariable Long id) {
        Coffee deleted = coffeeService.delete(id);
        return (deleted != null)?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build():
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/api/transaction-coffee")
    public ResponseEntity<List<Coffee>> transaction(@RequestBody List<CoffeeDto> dtos){
        List<Coffee> savedList = coffeeService.saveCoffees(dtos);
        return (savedList != null)?
                ResponseEntity.status(HttpStatus.OK).body(savedList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
