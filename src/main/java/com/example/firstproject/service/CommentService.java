package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service //Service로 선언
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;
    //게시글 Repository가 있어야 댓글(comment)을 생성할 때 대상 게시글(article)의 존재 여부를 파악할 수 있기 때문

    //조회
    public List<CommentDto> comments(Long articleId) { //댓글을 조회하는 코드
        /*//1.댓글 조회
        List<Comment> comments = commentRepository.findByArticleId(articleId); //articleId번 게시글의 모든 댓글
        //List<Comment>타입인 이유: 한 게시글에 여러 댓글이 달릴 수 있기 때문에 여러 Comment를 리스트로 받기 위함

        //2.엔티티 -> DTO반환
        List<CommentDto> dtos = new ArrayList<CommentDto>();
        for (int i = 0; i < comments.size(); i++){ //조회한 댓글 엔티티의 개수만큼 반복
            Comment c = comments.get(i); //조회한 댓글 엔티티 목록에서 엔티티를 하나씩 꺼내 변수 c에 저장
            CommentDto dto = CommentDto.createCommentDto(c); //엔티티를 DTO로 변환해 그 결과를 dto 변수에 저장
            dtos.add(dto);
        }//for문이 수행돠면 모든 댓글 엔티티가 DTO로 변환되고, 변환된 DTO가 dtos에 저장*/

        //3.결과반환
        return commentRepository.findByArticleId(articleId) //댓글 엔티티 목록 조회
                .stream() //댓글 엔티티 목록을 스트림으로 변환
                .map(comment -> CommentDto.createCommentDto(comment)) //엔티티를 DTO로 매핑
                .collect(Collectors.toList()); //스트림을 리스트로 변환
    }

    @Transactional //생성
    public CommentDto create(Long articleId, CommentDto dto) {
        //1.게시글 조회 및 예외 발생
        Article article = articleRepository.findById(articleId)
                .orElseThrow(()-> new IllegalArgumentException("댓글 생성 실패!" + "대상 게시글이 없습니다."));
        //2.댓글 엔티티 생성 - 게시글을 찾았을 때
        Comment comment = Comment.createComment(dto, article);
        //3.댓글 엔티티를 DB에 저장
        Comment created = commentRepository.save(comment);
        //4. DTO로 변환해 반환
        return CommentDto.createCommentDto(created);
    }
    @Transactional//수정
    public CommentDto update(Long id, CommentDto dto) {
        //1.댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id) //수정할 댓글 가져오기
                .orElseThrow(()-> new IllegalArgumentException("댓글 수정 실패!" +
                        "대상 댓글이 없습니다."));
        //2.댓글 수정
        target.patch(dto); //기존 댓글 엔티티에 수정 정보 추가
        //3.DB로 갱신(수정 데이터 덮어쓰기)
        Comment updated = commentRepository.save(target);
        //4.댓글 엔티티를 DTO로 변환 및 컨트롤러로 반환
        return CommentDto.createCommentDto(updated); //새로운 DTO를 생성해 반환
    }
    @Transactional
    public CommentDto delete(Long id) {
        //1.댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id) //삭제할 댓글 가져오기
                .orElseThrow(()-> new IllegalArgumentException("댓글 삭제 실패!" +
                        "대상이 없습니다."));
        //2.댓글 삭제
        commentRepository.delete(target);
        //3.삭제 댓글을 DTO로 변환 및 반환
        return CommentDto.createCommentDto(target);
    }
}
