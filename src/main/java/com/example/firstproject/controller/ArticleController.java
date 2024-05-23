package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j //로깅 기능을 위한 어노테이션 추가
@Controller
public class ArticleController {
    @Autowired //해당 어노테이션을 붙이면 스프링 부트가 미리 생성해 놓은 객체를 가져다가 연결해줌 - 의존성 주입
    private ArticleRepository articleRepository; //articleRepository 객체 선언
    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){ //폼 데이터를 DTO로 받기
        log.info(form.toString());  //로깅 코드 추가
        //System.out.println(form.toString()); ///DTO에 폼 데이터가 잘 담겼는지 확인
        //1. DTO를 엔티티로 변환 - 엔티티 클래스 생성
        Article article = form.toEntity();
        log.info(article.toString());
        //System.out.println(article.toString()); //DTO가 엔티티로 잘 변환되는지 확인 출력
        //2. 리포지토리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
//        System.out.println(saved.toString());
        return "";
    }
}
