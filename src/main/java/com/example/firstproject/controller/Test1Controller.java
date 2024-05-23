package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Test1Controller {

    @GetMapping("/Test1/test1")
    public String newTest1Form(){
        return "Test1/test1";
    }
//    @PostMapping("/test/create")
//    public String CreateTest(){
//        return "";
//    }

}
// action="/test/create" method="post"