package com.example.firstproject.service;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    public List<Coffee> index() { //조회
        return coffeeRepository.findAll();
    }

    public Coffee save(CoffeeDto dto) { //생성
        Coffee coffee = dto.toEntity();
        if(coffee.getId() != null){
            return null;
        }
        return coffeeRepository.save(coffee);
    }

    public Coffee update(Long id, CoffeeDto dto) {//수정
        //수정하기 위해서는 원래 데이터를 먼저 가져온다.
        Coffee coffee = dto.toEntity();
        log.info("id:{}, coffee: {}", id, coffee.toString());
        //가져온 데이터가  repository에 있는지 없는지 확인한다.
        Coffee target = coffeeRepository.findById(id).orElse(null);
        //있으면 변경하고 없으면 처리하기
        if(target == null || id != coffee.getId()){
            log.info("잘못된 요청! id: {}, article: {}", id, coffee.toString());
            return null;
        }
        target.patch(coffee);
        Coffee updated = coffeeRepository.save(target);
        return updated;
    }
    public Coffee delete(Long id) {//삭제
        Coffee target = coffeeRepository.findById(id).orElse(null);
        if (target == null) { //수정할 대상의 엔티티가 없으면
            return null;
        }
        coffeeRepository.delete(target);
        return target;
    }

    @Transactional
    public List<Coffee> saveCoffees(List<CoffeeDto> dtos) {
        List<Coffee> coffeeList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());
        coffeeList.stream().forEach(coffee -> coffeeRepository.save(coffee));
        coffeeRepository.findById((-1L)).orElseThrow(() -> new IllegalArgumentException());
        return coffeeList;
    }
}
