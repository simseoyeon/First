package com.example.firstproject.dto;

import com.example.firstproject.entity.Member;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class MemberForm {
    private String email;   //email을 받을 필드
    private String password;  //password를 받을 필드

    //전송받은 email과 password를 필드에 저장하는 생성자 추가
//    public MemberForm(String email, String password) {
//        this.email = email;
//        this.password = password;
//    }

//    @Override
//    public String toString() {
//        return "MemberForm{" +
//                "email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                '}';
//    }
    public Member toEntity() {
        return new Member(null, email, password); //생성자 입력 양식에 맞게 작성
    }
}
