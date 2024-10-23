package com.example.demo.controller;

import com.example.demo.domain.Member;
import com.example.demo.dto.MemberRequest;
import com.example.demo.repository.MemberRepostiory;
import com.example.demo.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepostiory memberRepostiory;
    private final MemberService memberService;
    @GetMapping("/member/signUp")
    public String signUpForm(){
        return "member/signUp";
    }
    @PostMapping("/member/signUp")
    public String create(@ModelAttribute MemberRequest memberRequest){
        Member member = memberRepostiory.findMemberByUserName(memberRequest.getUserName());
        if(member == null){
            memberService.create(memberRequest);
        }
        else{
            return "member/signUpError";
        }

        return "redirect:main";
    }
    @GetMapping("/member/signUpError")
    public String loginError(){
        return "member/signUpError";
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberRequest memberRequest, HttpSession httpSession){
        Member member = memberRepostiory.findMemberByUserName(memberRequest.getUserName());
        if(member==null){
            return "member/loginError";
        }
        if(member.getPassword().equals(memberRequest.getPassword())){
            httpSession.setAttribute("loginMember",member);
            return "redirect:/main";
        }
        return "member/loginError";
    }
}
