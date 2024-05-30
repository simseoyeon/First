package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository; //게시글 리파지터리 객체 주입

    public List<Article> index() {
        return articleRepository.findAll(); //데이터는 리파지터리를 통해서 가져오기때문에 DB에서 조회한 결과를 반환함
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity(); //dto를 Entity로 변환한 값을 article에 저장
        if (article.getId() != null) { //article(Entity)의 id가 null값이 아니라면(id가 존재한다면) null을 반환하는 코드
            return null; //article 데이터가 id가 있는 값이라면 null로 반환한다.
            //만약 id = 1인 값을 post(생성)하려고 입력하면 이미 id=1인 데이터가 있기 때문에 오류 발생
        }
        return articleRepository.save(article); //엔티티로 변환된 article을 DB에 저장
    }

    public Article update(Long id, ArticleForm dto) {
        //1.수정용 엔티티 생성하기
        Article article = dto.toEntity(); //수정데이터가 담긴 dto를 엔티티로 변환 후 article에 저장
        log.info("id: {}, article: {}", id, article.toString());
        //2.DB에 대상 엔티티가 있는지 조회하기(타깃조회)
        Article target = articleRepository.findById(id).orElse(null);
        //3.대상 엔티티가 없거나 수정하려는 id가 잘못됐을 경우 처리하기
        if (target == null || id != article.getId()) {
            //400, 잘못된 요청 응답!
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString()); //잘못된 요청 로그 찍기
            return null;
        }
        //4.대상 엔티티가 있으면 수정 내용으로 업데이트하고 정상 응답(200) 보내기
        target.patch(article); //일부 데이터 수정
        Article updated = articleRepository.save(target); //수정 내용 DB에 최종 저장
        return updated;
    }

    public Article delete(Long id) {
        //1.DB에서 대상 엔티티가 있는지 조회
        Article target = articleRepository.findById(id).orElse(null);
        //2.대상 엔티티가 없어서 요청 자체가 잘못됐을 경우 처리
        if (target == null) {
            return null;
        }
        //3.대상 엔티티가 있으면 삭제하고 정상 응답(200) 반환
        articleRepository.delete(target);
        return target;
    }
    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        //1. dto 묶음을 엔티티 묶음으로 변환하기
        List<Article> articleList = dtos.stream()//dtos를 스트림화 한다.
                .map(dto -> dto.toEntity()) //map()으로 dto가 하나하나 올 때마다 dto.toEntity()를 수행해 매핑
                .collect(Collectors.toList()); //매핑한 dto를 리스트로 묶는다.
                //리스트로 묶은 매핑한 dto를 articleList에 저장
        //2. 엔티티 묶음을 DB에 저장하기
        articleList.stream().forEach(article -> articleRepository.save(article));
        //3. 강제 예외 발생시키기
        articleRepository.findById((-1L)).orElseThrow(() -> new IllegalArgumentException("결제 실패!"));
        //4. 결과 값 반환하기
        return articleList;
    }
}