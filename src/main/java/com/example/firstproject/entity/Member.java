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
@NoArgsConstructor
@ToString
@Entity
@Getter
public class Member {
    @Id //엔티티의 대푯값 지정 (SQL에서의 PK 기능)
    @GeneratedValue //자동생성 기능 추가(숫자가 자동으로 매겨짐) - 데이터를 구분할 수 있는 숫자
    //숫자가 자동으로 매겨져서 어떤 숫자가 나올지 모르는 상황 -> 정수타입 중 가장 큰 Long타입을 사용하는 이유
    private Long id;

    @Column  //열 생성
    private String email;

    @Column
    private String password;

    public Long getId() {
        return id;
    }

    //public Long getId() {
    //    return id;
    //}

    //member생성자 추가
//    public Member(Long id, String email, String password) {
//        this.Id = id;
//        this.email = email;
//        this.password = password;
//    }
//
//    //toString()메서드 추가
//    @Override
//    public String toString() {
//        return "Member{" +
//                "Id=" + Id +
//                ", email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                '}';
//    }
}
