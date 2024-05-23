package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Test1Controller {

    @GetMapping("/tests/test1")
    public String Test1Form(){
        return "tests/test1";
    }
    @PostMapping("/test/create")
    public String CreateTest(){
        return "";
    }

}
