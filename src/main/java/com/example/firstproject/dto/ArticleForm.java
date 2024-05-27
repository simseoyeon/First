package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor //article생성자를 대체하는 어노테이션
@ToString //toString()메서드를 대체하는 어노테이션
public class ArticleForm { //폼 데이터를 받아올 그릇
    private Long id;
    private String title; //제목을 받을 필드
    private String content; //내용을 받을 필드
    //전송받은 제목과 내용을 필드에 저장하는 생성자 추가
//    public ArticleForm(String title, String content) {
//        this.title = title;
//        this.content = content;
//    }
    //데이터를 잘 받았는지 확인할 toString()메서드 추가
//    @Override
//    public String toString() {
//        return "ArticleForm{" +
//                "title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                '}';
//    }

    public Article toEntity() {
        //toEntity()는 DTO인 form객체를 엔티티 객체로 변환하는 역할
        return new Article(id, title, content);
        //폼 데이터를 담은 DTO 객체를 엔티티로 반환한다.
        //전달 값은 Article클래스의 생성자 형식에 맞게 작성
        //Article클래스에서 id, title, content를 매개변수로 갖고 있어서 변환 값에도 똑같이 작성해야 한다.
        //ArticleForm 객체에 id 정보는 없어서 null값으로 입력
    }
}
