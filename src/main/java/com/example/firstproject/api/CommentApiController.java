package com.example.firstproject.api;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //Rest컨트롤러임을 선언
public class CommentApiController { //컨트롤러가 처리하는 기능 - 조회, 생성, 수정, 삭제
    @Autowired
    private CommentService commentService; //service와 협업할 수 있도록 commentService객체 주입

    //1.댓글 조회 - Get
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId){ //몇번 게시글을 조회하는지 알아야 하므로 articleId를 객체로 받는다.
        //서비스에 위임
        List<CommentDto> dtos = commentService.comments(articleId);
        //결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    //2.댓글 생성
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> create (@PathVariable Long articleId, @RequestBody CommentDto dto){
        //서비스에 위임
        CommentDto createdDto = commentService.create(articleId, dto); //몇번 게시글에 어떤 값을 생성하는지 넘겨줌
        //결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(createdDto);
    }

    //3.댓글 수정
    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestBody CommentDto dto){
        //수정 작업을 서비스에 위임
        CommentDto updateDto = commentService.update(id, dto);
        //결과를 클라이언트에 응답
        return ResponseEntity.status(HttpStatus.OK).body(updateDto);
    }

    //4.댓글 삭제
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id){
        //삭제 작업을 서비스에 위임
        CommentDto deleteDto = commentService.delete(id);
        //결과를 클라이언트에 응답
        return ResponseEntity.status(HttpStatus.OK).body(deleteDto);
    }
}
