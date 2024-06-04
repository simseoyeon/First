package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j //로깅 기능을 위한 어노테이션 추가
@Controller
public class ArticleController {
    @Autowired //해당 어노테이션을 붙이면 스프링 부트가 미리 생성해 놓은 객체를 가져다가 연결해줌 - 의존성 주입
    private ArticleRepository articleRepository; //articleRepository 객체 선언
    @Autowired
    private CommentService commentService;
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
        //article은 Entity에 들어 있는 객체
        //saved객체에는 articleRepository에서 연동된 DB가 저장된다.
        log.info(saved.toString());
//        System.out.println(saved.toString());
        return "redirect:/articles/" + saved.getId(); //리다이렉트를 작성할 위치
        //id값을 가져오기 위해 saved객체 이용(id에 따라 URL주소가 달라지도록 +연산자 사용)
    }

    //상세페이지를 받는 컨트롤러
    @GetMapping("/articles/{id}") //데이터 조회 요청 접수
        //URL요청 받는 GetMapping
        //중괄호 안에 id를 작성하면 id는 변수로 사용 가능하다.
        //즉 괄호 안의 URL은 id에 따라 articles/1, articles/2 등으로 받는다는 의미
    public String show(@PathVariable Long id, Model model){ //매개변수로 id 받아오기
            //@PathVariable : URL 요청으로 들어온 전달값(id)을 컨트롤러의 매개변수로 가져오는 어노테이션
        log.info("id = " + id);    //id를 잘 받았는지 확인하는 로그 찍기
        //1. id를 조회해 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentDtos = commentService.comments(id);
            //DB에서 데이터를 가져오는 주체 : 리파지터리
            //findById(): JPA의 CrudRepository가 제공하는 메서드
                // -> 특정 엔티티의 id 값을 기준으로 데이터를 찾아 Optional 타입으로 반환
            //article 타입의 articleEntity객체에 값을 저장한다.
            //그 값은 articleRepository의 사용 메서드인 findById로 id값의 데이터를 받는다. 엔티티의 Id가 아니라면 null값을 갖는다.
            //.orElse() : id값으로 데이터를 찾을 때 해당 id값이 없으면 null을 반환
                //-> 조회한 결과 값이 있으면 articleEntity 변수에 값을 넣고, 없으면 null을 저장
        //2. 모델에 데이터 등록하기 -> why? MVC 패턴에 따라 조회한 데이터를 뷰 페이지에 사용하기 위해
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentDtos);
            //id로 DB에서 조회한 데이터는 모델에 article이라는 이름으로 등록
        //3. 뷰 페이지 반환하기
        return "articles/show";
            //모델에 등록한 데이터를 뷰 페이지에서 사용할 수 있게 설정 - mustache파일 사용
    }
    @GetMapping("/articles")
    public String index(Model model){
        //1. 모든 데이터 가져오기
        List<Article> articleEntityList = articleRepository.findAll();
            //findAll()메서드를 통해 해당 리포지터리에 있는 모든 데이터를 가져옴
            //가져 온 모든 데이터는 articleEntityList에 저장
            //이때 데이터 묶음의 타입은 Article타입
            //findAll()메서드가 반환하는 데이터 타입은 Iterable인데 작성 타입은 List라서 서로 불일치한다는 메서드
        //2. 모델에 데이터 등록하기
        model.addAttribute("articleList", articleEntityList);
        //3. 뷰 페이지 설정하기
        return "articles/index";
    }
    //수정페이지 만들고 기존 데이터 불러오기
    @GetMapping("/articles/{id}/edit")//2.URL요청 접수 - show.mustache에 있는 edit의 링크
    public String edit(@PathVariable Long id, Model model){  //1.메서드 생성 및 뷰 페이지 설정 4. id를 매개변수로 받아오기
        //5.모델 객체 받아오기
        //3.DB에서 수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        //6.모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);
        //1.뷰 페이지 설정하기
        return "articles/edit";
    }
    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        log.info(form.toString());
        //1.DTO를 엔티티로 변환하기
        Article articleEntity = form.toEntity(); //DTO를 엔티티로 변환하기
        log.info(articleEntity.toString()); //엔티티로 잘 변환됐는지 로그 찍기
        //2.엔티티를 DB에 저장하기 - 기존 데이터를 바꾼다.
        //2-1. DB에서 기존 데이터 가져오기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        //2-2. 기존 데이터 값을 갱신하기
        if(target != null){   //수정 시 입력 대상의 존재 여부를 검증하도록 if 조건문
            articleRepository.save(articleEntity); //엔티티를 DB에 저장(갱신)
        }
        //3.수정 결과 페이지로 리다이렉트하기
        return "redirect:/articles/" + articleEntity.getId();
    }
    //Delete 요청을 받아 데이터 삭제
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제 요청이 들어왔습니다!!");
        //1.삭제할 대상 가져오기 - Repository에서 삭제할 데이터 찾아서 target에 저장
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString()); //target에 데이터가 저장되었는지 확인하는 로깅
        //2.대상 엔티티 삭제하기
        if (target != null){
            articleRepository.delete(target); //delete로 대상 삭제
            rttr.addFlashAttribute("msg", "삭제됐습니다!"); //리다이렉트 시점에 한 번만 사용할 데이터 등록가능(휘발성 데이터)
        }
        //3.결과 페이지로 리다이렉트하기 - 게시글 삭제 후 목록페이지로 돌아가기
        return "redirect:/articles";
    }
}
