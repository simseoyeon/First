package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller    //컨트롤러 선언
public class FirstController {

    @GetMapping("/hi") //클라이언트의 요청을 받음
    public String niceToMeetYou(Model model){   //메서드 작성 모델 객체 받아오기
        //model객체가 "홍팍" 값을 username에 연결해 웹 브라우저로 보냄
        model.addAttribute("username", "홍팍");
        return "greetings";    //greetings.mustache 파일 반환
    }

    @GetMapping("/bye") //클라언트의 요청을 bye페이지로 받음
    public String seeYounext(Model model){  //seeYounext메서드 작성, 모델 객체 받아오기
        model.addAttribute("nickname", "홍길동");
        return "goodbye";  //goodbye.mustache 파일로 반환
    }
}
