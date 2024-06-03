package com.example.firstproject.repository;

import com.example.firstproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    //JpaRepository를 상속받음으로써 CRUD작업+페이지처리+정렬작업
    //Comment의 대푯값은 ID로 Long타입
    // -> 구현체(CommentRepository)는 Entity클래스와 ID클래스에 대한 정보를 알고 있어서 런타임 시점에 적절한 쿼리를 생성하고 실행할 수 있다.

    //특정 게시글의 모든 댓글 조회 - @Query 어노테이션 이용
    @Query(value = "SELECT*FROM comment WHERE article_id = :articleId", nativeQuery = true)
    //comment테이블의 article_id을 기준으로 한 데이터 값을 사용하겠다.
    List<Comment> findByArticleId(Long articleId);
    //특정 닉네임의 모든 댓글 조회 - orm.xml 파일 이용
    List<Comment> findByNickname(String nickname);
}
