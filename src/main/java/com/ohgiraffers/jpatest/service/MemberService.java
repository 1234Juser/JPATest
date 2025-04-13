package com.ohgiraffers.jpatest.service;

import com.ohgiraffers.jpatest.dto.MemberDTO;
import com.ohgiraffers.jpatest.entity.MemberEntity;
import com.ohgiraffers.jpatest.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    //회원가입
    public int regist(MemberDTO memberDTO) {
        int result = 0;
        try {
            //insert, update
            MemberEntity memberEntity = memberRepository.save(new MemberEntity(memberDTO));
            log.info("service entity: {} " + memberEntity);
        }catch (Exception e) {
            result = 1;
            e.printStackTrace();
        }
        return result;
    }
    //로그인
    public boolean login(String userId, String password) {
        MemberEntity entity = memberRepository.findByUserId(userId);
        if (entity != null && entity.getPassword().equals(password)) {
            return true;
        }

        return false;
    }

    // 전체 회원 목록 조회
    public List<MemberDTO> getList(){
        List<MemberDTO> list = null;
        List<MemberEntity> listE = memberRepository.findAll();
        if(listE.size()!=0){
            list = listE.stream()
                    .map(m -> new MemberDTO(m))
                    .toList();
        }
        log.info("list entity : {}", listE);
        return list;
    }

    // 단일 회원 목록 조회
    public MemberDTO getoneList(long number) {
        Optional<MemberEntity> opM = memberRepository.findById(number);
        MemberEntity memberEntity = opM.orElse(null);
        if(memberEntity!= null){
            return new MemberDTO(memberEntity);
        }
        return null;
    }

    // 회원 수정 (userId 기준으로 이름/나이 수정)
    public int updateData(String userId, MemberDTO memberDTO){
        MemberEntity entity = memberRepository.findByUserId(userId);
        log.info("service update : {}", entity);
        if (entity != null) {
            entity.setUserName(memberDTO.getUserName());
            entity.setAge(memberDTO.getAge());
            memberRepository.save(entity);
            return 1;
        }
        return 0;
    }
    // 회원 삭제
    public int deleteData(Long num) {
        int result = 0;
        MemberDTO memberDTO = getoneList(num);
        if(memberDTO != null){
            memberRepository.deleteById(num);
            result = 1;
        }
        return result;
    }

    // 페이징 조회
    public List<MemberDTO> getListPage(int start, int page) {
        Pageable pageable = PageRequest.of(start, page,
                Sort.by(Sort.Order.desc("number")));
        Page<MemberEntity> pageEntity = memberRepository.findAll(pageable);
        List<MemberEntity> listEntity = pageEntity.getContent();
        return listEntity.stream()
                .map(m -> new MemberDTO(m))
                .toList();
    }

    //nativequery로 회원수정
    public int updateContent(MemberDTO memberDTO) {
        try {
            return memberRepository.updateContent(memberDTO.getUserId(), memberDTO.getUserName(),
                    memberDTO.getAge(), memberDTO.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


}
