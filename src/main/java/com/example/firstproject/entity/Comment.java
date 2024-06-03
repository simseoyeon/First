package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id //대표키 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB가 자동으로 1씩 증가
    private Long id; //대표키
    @ManyToOne //Comment 엔티티와 Article 엔티티를 다대일 관계로 설정
    @JoinColumn(name="article_id") //외래키 생성, Article 엔티티의 기본키(id)와 매핑
    private Article article; //해당 댓글의 부모 게시글
    //Comment 엔티티로 생성될 DB 테이블에 article_id라는 속성이 만들어짐.
    //여기에 저장된 값을 Article엔티티의 대표키인 id와 매핑 - article_id=id라면 해당 게시글에 대한 댓글인지 알 수 있음
    @Column //해당 필드를 테이블의 속성으로 매핑
    private String nickname; //댓글을 단 사람
    @Column //해당 필드를 테이블의 속성으로 매핑
    private String body; //댓글 본문

    public static Comment createComment(CommentDto dto, Article article) {
        //예외발생
        if(dto.getId() != null)
            throw new IllegalArgumentException("댓글 생성 실패! 댓글이 id가 없어야 합니다.");
        if(dto.getArticleId() != article.getId())
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못됐습니다.");
        //엔티티 생성 및 반환
        return new Comment(
                dto.getId(),
                article,
                dto.getNickname(),
                dto.getBody()
        );
    }

    public void patch(CommentDto dto) { //엔티티를 수정할 수 없는 경우에 예외 발생, 문제가 발생하지 않았다면 내용 수정
        //예외 발생
        if(this.id != dto.getId())
            throw  new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력됐습니다.");
        //객체 갱신
        if(dto.getNickname() != null) //수정할 닉네임 데이터가 있다면
            this.nickname = dto.getNickname();
        if(dto.getBody() != null) //수정할 본문 데이터가 있다면
            this.body = dto.getBody();
    }
}
