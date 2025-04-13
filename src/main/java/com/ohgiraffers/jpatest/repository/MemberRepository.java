package com.ohgiraffers.jpatest.repository;

import com.ohgiraffers.jpatest.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    // 사용자 ID로 검색
    MemberEntity findByUserId(String userId);


    // native query로 수정
    @Modifying
    @Transactional
    @Query(value = "UPDATE jpa_test " +
            "SET user_name = :name, age = :age, password = :password " +
            "WHERE user_id = :id",
            nativeQuery = true)
    int updateContent(@Param("id") String userId,
                      @Param("name") String userName,
                      @Param("age") int age,
                      @Param("password") String password);
}
