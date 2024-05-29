package com.example.firstproject.api;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
public class CoffeeApiController {
    private CoffeeRepository coffeeRepository;

    //GET - 조회
    @GetMapping("/api/Coffees")  //coffee의 데이터 전체 조호ㅣ
    public List<Coffee> index(){
        return coffeeRepository.findAll();
    }
    //POST - 생성
    @PostMapping("/api/coffees")
    public Coffee create(@RequestBody CoffeeDto dto){
        Coffee coffee = dto.toEntity();
        return coffeeRepository.save(coffee);
    }
    //PATCH - 수정
    @PatchMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> update(@PathVariable Long id, @RequestBody CoffeeDto dto){
        //수정하기 위해서는 원래 데이터를 먼저 가져온다.
        Coffee coffee = dto.toEntity();
        log.info("id:{}, coffee: {}", id, coffee.toString());
        //가져온 데이터가  repository에 있는지 없는지 확인한다.
        Coffee target = coffeeRepository.findById(id).orElse(null);
        //있으면 변경하고 없으면 처리하기
        if(target == null || id != coffee.getId()){
            log.info("잘못된 요청! id: {}, article: {}", id, coffee.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        target.patch(coffee);
        Coffee updated = coffeeRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated)
        //DELETE - 삭제
    }


}
