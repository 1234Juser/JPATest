package com.ohgiraffers.jpatest.dto;

import com.ohgiraffers.jpatest.entity.MemberEntity;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
    private Long number;
    private String userId;
    private String password;
    private String userName;
    private int age;

    public MemberDTO(MemberEntity entity){
        this.number = entity.getNumber();
        this.userId = entity.getUserId();
        this.password = entity.getPassword();
        this.userName = entity.getUserName();
        this.age = entity.getAge();

    }


}
