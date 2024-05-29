package com.example.firstproject.entity;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)//자동 생성 기능 추가, DB가 id 자동생성

    /*기본키를 자동으로 생성하는 방법 4가지
    기본키를 자동으로 생성할 때에는 @ID와 @GeneratedValue 어노테이션이 함께 사용되어야 한다.

    1. IDENTITY - @GeneratedValue(strategy = GenerationType.IDENTITY)
        기본키 생성을 데이터베이스에게 위임하는 방식으로 id값을 따로 할당하지 않아도
        데이터베이스가 자동으로 AUTO_INCREMENT하여 기본키를 생성해준다.
    2. SEQUENCE - @GeneratedValue(strategy = GenerationType.SEQUENCE)
    3. TABLE - @GeneratedValue(strategy = GenerationType.TABLE)
    4. AUTO - @GeneratedValue(strategy = GenerationType.AUTO)
    */
    private Long id;
    @Column //title필드 선언, DB테이블의 title열과 연결
    private String title;
    @Column //content필드 선언, DB테이블의 content열과 ㅇ연결
    private String content;

    public Long getId() { //데이터 타입을 String -> Long타입으로 변경
        return id;
    }

    public void patch(Article article) {
        if(article.title != null)
            this.title = article.title;
        if(article.content !=null)
            this.content = article.content;
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
