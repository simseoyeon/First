package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class MemberController {
    @Autowired
    private MemberRepository memberRepository; //memberRepository 객체 선언

    //mustache와 연결 -> member안에 있는 new이름의 mustache와 연결하겠다는 의미
    @GetMapping("/members/new")
    public String MemberForm(){
        return "members/new";  //-> member안에 있는 new이름의 mustache와 연결하겠다는 의미
    }
    //form데이터 받기
    //mushtache에서 /join으로 post방식으로 받겠다고 작성
    @PostMapping("/join")
    public String JoinMember(MemberForm form){ //폼 데이터를 DTO로 받기
        log.info(form.toString());
//        System.out.println(form.toString()); //DTO에 폼 데이터가 잘 담겼는지 확인
        //1. DTO를 엔티티로 변환
        Member member = form.toEntity();
        log.info(form.toString());
//        System.out.println(member.toString());
        //2. 라퍼지토리로 엔티티를 DB에 저장
        Member saved = memberRepository.save(member); //member 엔티티에 저장해 saved 객체에 반환
        log.info(saved.toString());
//        System.out.println(saved.toString());
        return "";
    }

}
