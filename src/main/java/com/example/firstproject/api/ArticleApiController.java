package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
public class ArticleApiController {
    @Autowired
    private ArticleRepository articleRepository;

    //GET - 데이터 조회
    @GetMapping("/api/articles")    //모든 게시물 조회
    public List<Article> index(){  //모든 게시물을 조회하기 위해서 메서드 수행 결과로 Article묶음을 반환
        return articleRepository.findAll();
    }
    //단일 게시물 조회 - 조회하려는 게시글의 id에 따라 URL요청이 다름
    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id){ //단일 Article을 반환
        return  articleRepository.findById(id).orElse(null);
    }

    //POST - 데이터 생성
    @PostMapping("/api/articles")
    public Article create(@RequestBody ArticleForm dto){ //RequestBody어노테이션을 통해 dto에 요청 본문이 그대로 전달
        Article article = dto.toEntity(); //수정할 데이터를 dto매개변수로 받아와서 엔티티로 변환해 변수 article에 넣고
        return articleRepository.save(article); //repository를 통해 DB에 저장한 후 반환
    }

    //PATCH - 데이터 수정
    //데이터 전체 수정
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){
        //1.수정용 엔티티 생성하기
        Article article = dto.toEntity(); //수정데이터가 담긴 dto를 엔티티로 변환 후 article에 저장
        log.info("id: {}, article: {}", id, article.toString());
        //2.DB에 대상 엔티티가 있는지 조회하기(타깃조회)
        Article target = articleRepository.findById(id).orElse(null);
        //3.대상 엔티티가 없거나 수정하려는 id가 잘못됐을 경우 처리하기
        if(target == null || id != article.getId()){
            //400, 잘못된 요청 응답!
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString()); //잘못된 요청 로그 찍기
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        //4.대상 엔티티가 있으면 수정 내용으로 업데이트하고 정상 응답(200) 보내기
        target.patch(article); //일부 데이터 수정
        Article updated = articleRepository.save(target); //수정 내용 DB에 최종 저장
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
    //target에는 기존 데이터, article에는 수정할 데이터

    //DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        //1.DB에서 대상 엔티티가 있는지 조회
        Article target = articleRepository.findById(id).orElse(null);
        //2.대상 엔티티가 없어서 요청 자체가 잘못됐을 경우 처리
        if(target == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        //3.대상 엔티티가 있으면 삭제하고 정상 응답(200) 반환
        articleRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
