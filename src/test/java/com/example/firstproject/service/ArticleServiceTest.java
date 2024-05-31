package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test; //Test 패키지 임포트
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //해당 클래스를 스프링부트와 연동해 통합 테스트를 수행하겠다고 선언
    //테스트 코드에서 스프링 부트가 관리하는 다양한 객체를 주입받을 수 있음
class ArticleServiceTest {
    @Autowired
    ArticleService articleService;

    @Test //해당 메서드가 테스트 코드임을 선언
    void index() {
        //1. 예상 데이터 - data.sql 파일에 있는 테이블 - 배열로 묶기
        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(2L, "나나나나", "2222");
        Article c = new Article(3L, "다다다다", "3333");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a, b, c));
        //Arrays.asList(): 입력된 배열 또는 2개 이상의 동일한 타입 데이터를 정적 리스트로 만들어 반환
        //정적 리스트는 고정 크기이므로 수정이나 삭제 불가능
        //a, b, c의 데이터를 정적 리스트로 만들어서 expected에 저장해 비교를 쉽게 하기 위해 해당 메서드 사용

        //2. 실제 데이터 - 모든 게시글을 조회 요청하고 그 결과로 반환되는 게시물 묶음을 받아오는 것
        List<Article> articles = articleService.index();

        //3. 비교 및 검증 - 예상 데이터와 실제 데이터를 비교해 일치하면 테스트를 통과시키는 메서드
        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    void show_성공_존재하는_id_입력() {
        //1.예상 데이터
        Long id = 1L;
        Article expected = new Article(id, "가가가가", "1111");
        //2.실제 데이터
        Article article = articleService.show(id);
        //3.비교 및 검증 - 예상 데이터가 담긴 객체(expected)와 실제 데이터가 담긴 객체 article를 비교
        assertEquals(expected.toString(), article.toString());
    }
    @Test
    void show_실패_존재하지않는_id_입력() {
        //1.예상 데이터
        Long id = -1L;
        Article expected = null; //DB에서 조회되는 내용이 없어 null을 반환
        //2.실제 데이터
        Article article = articleService.show(id);
        //3.비교 및 검증
        assertEquals(expected, article); //null값은 toString()메서드를 호출할 수 없다.
        //articleService.show()에서 DB에 id값이 없으면 null값을 반환한다고 했고 (실제 데이터)
        //예상 데이터에서 id값이 DB에서 존재하지 않으면 null값으로 반환한다고 했다.
        //=> 비교를 했을 때 예상데이터도 null이고 실제 데이터도 null이니 테스트에 통과
    }

    //create()실행 후 id가 4인 데이터가 저장되어서  index()메서드에 오류 발생 => 트랜잭션을 통한 롤백 필요
    //트랜잭션을 통해서 변경된 데이터를 롤백 - 생성, 수정, 삭제의 경우 반드시 트랜잭션
    @Test
    @Transactional
    void create_성공_title과_content만_있는_dto_입력() {
        //1.예상데이터 - 사용자가 새 게시물을 생성한 상황을 가정해 작성

        String title = "라라라라";
        String content = "4444";

        ArticleForm dto = new ArticleForm(null, title, content); //dto생성
        //ArtilceForm을 통해서 받아온 데이터를 엔티티로 변환해서 DB에 저장한 dto - DB에 새로운 데이터를 생성
        Article expected = new Article(4L, title, content);
        //-> create_성공_title과_content만_있는_dto_입력()메서드 안에 있는 필드의 값을 받은 expected

        //2.실제데이터
        Article article = articleService.create(dto);

        //3.비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void create_실패_id가_포함된_dto_입력() {
        //1.예상 데이터
        Long id = 4L;
        String title = "라라라라";
        String content = "4444"; //id, title, content를 임의로 배정
        ArticleForm dto = new ArticleForm(id, title, content); //dto에 담기
        Article expected = null; //예상 데이터에 저장
        //2.실제 데이터
        Article article = articleService.create(dto);
        //3.비교 및 검증
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void update_성공_존재하는_id와_title_content가_있는_dto_입력() {
        //1.예상데이터
        Long id = 1L;
        String title = "가가가가";
        String content = "4444";

        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = new Article(1L, title, content);
        //2.실제데이터
        Article article = articleService.update(id, dto);
        //3.비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }
    @Test
    @Transactional
    void update_성공_존재하는_id와_title만_있는_dto_입력() {
        //1.예상데이터
        Long id = 1L;
        String title = "가가가가";
        //String content = null;

        ArticleForm dto = new ArticleForm(id, title, null);
        Article expected = new Article(1L, "가가가가", "1111");
        //2.실제데이터
        Article article = articleService.update(id, dto);
        //3.비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }
    @Test
    @Transactional
    void update_실패_존재하지_않는_id의_dto_입력() {
        //1.예상데이터
        Long id = -1L;
        String title = "가나다라";
        String content = "1234";

        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null;
        //2.실제데이터
        Article article = articleService.update(id, dto);
        //3.비교 및 검증
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void delete_성공_존재하는_id_입력() {
        Long id = 1L;
//        String title = "가가가가";
//        String content = "1111";
//
//        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = new Article(1L, "가가가가", "1111");

        Article article = articleService.delete(id);

        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void delete_실패_존재하지_않는_id_입력() {
        Long id = -1L;
//        String title = "가가가가";
//        String content = "1111";
//        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null;

        Article article = articleService.delete(id);

        assertEquals(expected, article);

    }
}