package com.example.demo.repository;

import com.example.demo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepostiory extends JpaRepository<Member, Long> {
    Member findMemberByUserName(String memberName);

}
