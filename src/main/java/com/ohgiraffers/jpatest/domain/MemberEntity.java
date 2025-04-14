package com.ohgiraffers.jpatest.domain;

import com.ohgiraffers.jpatest.dto.MemberDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "jpa_test")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;

    @NotNull
    @Column( unique=true, length = 20 )
    private String userId;

    @Column(nullable = false, length = 100)
    private String password;


    @Column(name="user_name", nullable=false, length = 30)
    private String userName;

    @Column(name="age")
    private int age;

    public MemberEntity(MemberDTO dto) {
        this.userId = dto.getUserId();
        this.userName = dto.getUserName();
        this.age = dto.getAge();
        this.password = dto.getPassword();
    }

}