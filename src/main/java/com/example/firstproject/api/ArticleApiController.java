package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.service.ArticleService;
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
    private ArticleService articleService; //서비스 객체 주입

    //GET - 데이터 조회
    @GetMapping("/api/articles")    //모든 게시물 조회
    public List<Article> index(){  //모든 게시물을 조회하기 위해서 메서드 수행 결과로 Article묶음을 반환
        return articleService.index(); //service의 메서드인 index로 반환
    }

    @GetMapping("/api/articles/{id}") //단일 게시물 조회 - 조회하려는 게시글의 id에 따라 URL요청이 다름
    public Article show(@PathVariable Long id){ //단일 Article을 반환
        return articleService.show(id);
    }

    //POST - 데이터 생성
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto){ //RequestBody어노테이션을 통해 dto에 요청 본문이 그대로 전달
        Article created = articleService.create(dto); //수정할 데이터를 dto매개변수로 받아와서 엔티티로 변환해 변수 article에 넣고
        return (created != null) ? //삼항 연산자를 사용해서 created가 null이 아니면 good을 null이면 bad를 반환
        ResponseEntity.status(HttpStatus.OK).body(created): //created != null => good에 해당 - 본문에 created를 실어서 보낸다.
        ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //created = null => bad에 해당 - 본문 없이 빌드만 해서 보낸다.
    }

    //PATCH - 데이터 수정
    //데이터 전체 수정
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){
        Article updated = articleService.update(id, dto); //서비스를 통해 게시글 수정
        return (updated != null)?
                ResponseEntity.status(HttpStatus.OK).body(updated) : //수정되면 정상
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //수정이 안되면 오류 응답
    }
    //target에는 기존 데이터, article에는 수정할 데이터

    //DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        Article deleted = articleService.delete(id);
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos){
        List<Article> createdList = articleService.createArticles(dtos); //서비스 호출
        return (createdList != null) ? //생성 결과에 따라 응답 처리
                //createArticles()메서드를 통해 createdList에 dtos 데이터를 저장했을 때 그 값이 null이 아니면 본문에 해당 내용을 입력한다.
                ResponseEntity.status(HttpStatus.OK).body(createdList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}

