package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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

    @GetMapping("/articles/{id}") //데이터 조회 요청 접수
        //URL요청 받는 GetMapping
        //중괄호 안에 id를 작성하면 id는 변수로 사용 가능하다.
        //즉 괄호 안의 URL은 id에 따라 articles/1, articles/2 등으로 받는다는 의미
    public String show(@PathVariable Long id, Model model){ //매개변수로 id 받아오기
            //@PathVariable : URL 요청으로 들어온 전달값(id)을 컨트롤러의 매개변수로 가져오는 어노테이션
        log.info("id = " + id);    //id를 잘 받았는지 확인하는 로그 찍기
        //1. id를 조회해 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
            //DB에서 데이터를 가져오는 주체 : 리파지터리
            //findById(): JPA의 CrudRepository가 제공하는 메서드
                // -> 특정 엔티티의 id 값을 기준으로 데이터를 찾아 Optional 타입으로 반환
            //article 타입의 articleEntity객체에 값을 저장한다.
            //그 값은 articleRepository의 사용 메서드인 findById로 id값의 데이터를 받는다. 엔티티의 Id가 아니라면 null값을 갖는다.
            //.orElse() : id값으로 데이터를 찾을 때 해당 id값이 없으면 null을 반환
                //-> 조회한 결과 값이 있으면 articleEntity 변수에 값을 넣고, 없으면 null을 저장
        //2. 모델에 데이터 등록하기 -> why? MVC 패턴에 따라 조회한 데이터를 뷰 페이지에 사용하기 위해
        model.addAttribute("article", articleEntity);
            //id로 DB에서 조회한 데이터는 모델에 article이라는 이름으로 등록
        //3. 뷰 페이지 반환하기
        return "articles/show";
            //모델에 등록한 데이터를 뷰 페이지에서 사용할 수 있게 설정
    }
    @GetMapping("/articles")
    public String index(Model model){
        //1. 모든 데이터 가져오기
        List<Article> articleEntityList = articleRepository.findAll();
            //findAll()메서드를 통해 해당 리포지터리에 있는 모든 데이터를 가져옴
            //가져 온 모든 데이터는 articleEntityList에 저장
            //이때 데이터 묶음의 타입은 Article타입
        //2. 모델에 데이터 등록하기
        model.addAttribute("articleList", articleEntityList);
        //3. 뷰 페이지 설정하기
        return "articles/index";
    }
}
