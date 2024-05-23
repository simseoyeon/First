package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository; //articleRepository 객체 선언
    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){ //폼 데이터를 DTO로 받기
        System.out.println(form.toString()); ///DTO에 폼 데이터가 잘 담겼는지 확인
        //1. DTO를 엔티티로 변환 - 엔티티 클래스 생성
        Article article = form.toEntity();
        System.out.println(article.toString()); //DTO가 엔티티로 잘 변환되는지 확인 출력
        //2. 리포지토리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article);
        System.out.println(saved.toString());
        return "";
    }
}
