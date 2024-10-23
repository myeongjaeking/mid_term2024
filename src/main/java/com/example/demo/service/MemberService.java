package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.dto.MemberRequest;
import com.example.demo.repository.MemberRepostiory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {
    private final MemberRepostiory memberRepostiory;

    public Long create(MemberRequest memberRequest){
        Member member = new Member();
        member.setUserName(memberRequest.getUserName());
        member.setPassword(memberRequest.getPassword());
        memberRepostiory.save(member);
        return member.getId();
    }


}
