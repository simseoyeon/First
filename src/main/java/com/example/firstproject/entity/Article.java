package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor   //기본 생성자 추가 어노테이션
@ToString
@Entity   //엔티티 선언
@Getter
public class Article {
    @Id             //엔티티의 대푯값 지정
    @GeneratedValue    //자동 생성 기능 추가(숫자가 자동으로 매겨짐)
    private Long id;
    @Column //title필드 선언, DB테이블의 title열과 연결
    private String title;
    @Column //content필드 선언, DB테이블의 content열과 ㅇ연결
    private String content;

    public Long getId() { //데이터 타입을 String -> Long타입으로 변경
        return id;
    }
    //Article 생성자 추가


//    public Article(Long id, String title, String content) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//    }
//    @Override
//    public String toString() {
//        return "Article{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                '}';
//    }

}
