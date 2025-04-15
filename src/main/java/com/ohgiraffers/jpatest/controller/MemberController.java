package com.ohgiraffers.jpatest.controller;

import com.ohgiraffers.jpatest.dto.MemberDTO;
import com.ohgiraffers.jpatest.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    //회원가입
    @PostMapping("/members")
    public ResponseEntity regist(@RequestBody
                                 MemberDTO memberDTO){
        log.info("ctrl dto : {}", memberDTO);
        int result = memberService.regist(memberDTO);
        if(result == 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입성공");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("회원가입실패");
    }
    //로그인
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody MemberDTO memberDTO) {
        log.info("로그인 요청: {}", memberDTO);
        boolean result = memberService.login(memberDTO.getUserId(), memberDTO.getPassword());
        if (result) {
            return ResponseEntity.ok("로그인 성공");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
    }


    // 전체 회원 목록 조회
    @GetMapping("/members")
    public ResponseEntity getlist(){
        List<MemberDTO> list = memberService.getList();
        return ResponseEntity.ok().body(list);
    }

    // 단일 회원 목록 조회
    @GetMapping("/members/{number}")
    public ResponseEntity getonelist(@PathVariable("number") long number){
        MemberDTO memberDTO = memberService.getoneList(number);
        if(memberDTO!=null){
            return ResponseEntity.status(HttpStatus.OK).body(memberDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("단일회원목록조회실패");
    }

    // 회원 수정 (userId로 검색해서 이름/나이 수정)
    @PutMapping("/members/{userId}")
    public ResponseEntity update(@PathVariable("userId") String userId,
                                 @RequestBody MemberDTO memberDTO){
        int result = memberService.updateData(userId, memberDTO);
        if(result == 1) {
            return ResponseEntity.status(HttpStatus.OK).body("수정성공");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 회원 삭제
    @DeleteMapping("/members/{num}")
    public ResponseEntity delete(@PathVariable("num") Long num){
        int result = memberService.deleteData(num);
        if(result == 1) {
            return ResponseEntity.status(HttpStatus.OK).body("삭제성공");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 페이징 조회
    @GetMapping("/list")
    public ResponseEntity getlistpage(@RequestParam(defaultValue ="0") int start,
                                 @RequestParam(defaultValue = "5") int page){
        log.info("ctrl start : {}", start);
        List<MemberDTO> list = memberService.getListPage(start, page);
        return ResponseEntity.ok().body(list);
    }

    //nativequery로 회원수정
    @PutMapping("/api/content")
    public ResponseEntity updateContent(@RequestBody MemberDTO memberDTO) {
        int result = memberService.updateContent(memberDTO);
        if (result == 1) {
            return ResponseEntity.ok("수정성공");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("수정실패");
    }


}
