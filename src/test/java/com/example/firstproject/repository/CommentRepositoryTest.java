package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;
    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")
    void findByArticleId() {
        //Case 1: 4번 게시글의 모든 댓글 조회
        {
            //1.입력데이터준비
            Long articleId = 4L;
            //2.실제데이터

            //3.예상데이터
            Article article = new Article(4L, "당신의 인생 영화는?", "댓글 고");

            Comment a = new Comment(1L, article, "Park", "굿 윌 헌팅");
            Comment b = new Comment(2L, article, "Kim", "아임 엠 샘");
            Comment c = new Comment(3L, article, "Choi", "쇼생크 탈출");
            List<Comment> expected = Arrays.asList(a, b, c);

            List<Comment> comments = commentRepository.findByArticleId(articleId);

            //4.비교 및 검증

            assertEquals(expected.toString(), comments.toString(), "4번 글 댓글 모두 출력.");
        }
        //Case2: 1번 게시글의 모든 댓글 조회
        {
            Long articleId = 1L;
            List<Comment> comments = commentRepository.findByArticleId(articleId);
            Article article = new Article(1L, "가가가가", "1111");
            List<Comment> expected = Arrays.asList();
            assertEquals(expected.toString(), comments.toString(),"1번 글은 댓글이 없음.");
        }

    }

    @Test
    void findByNickname() {

    }
}