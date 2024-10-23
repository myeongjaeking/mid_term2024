package com.example.demo.controller;

import com.example.demo.domain.Item;
import com.example.demo.domain.Member;
import com.example.demo.domain.MemberVote;
import com.example.demo.domain.Vote;
import com.example.demo.dto.VoteRequest;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.MemberVoteRepository;
import com.example.demo.repository.VoteRepository;
import com.example.demo.service.ItemService;
import com.example.demo.service.VoteService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class VoteController {
    private final VoteRepository voteRepository;
    private final VoteService voteService;
    private final ItemRepository itemRepository;
    private final MemberVoteRepository memberVoteRepository;
    private final ItemService itemService;

    @GetMapping("/vote/create")
    public String createForm() {
        return "vote/create";
    }

    @PostMapping("/vote/create")
    public String create(@RequestParam(name = "title") String title, @RequestParam(name = "content") List<String> itmes, HttpSession httpSession) {
        Member member = (Member) httpSession.getAttribute("loginMember");
        System.out.println(member.getUserName());

        if (member != null) {
            voteService.create(title, itmes, member);
            return "redirect:/main";
        }
        return null;
    }

    @GetMapping("/vote/delete/{id}")
    public String delete(@PathVariable("id") Long id, HttpSession httpSession) {
        Member member = (Member) httpSession.getAttribute("loginMember");
        System.out.println(member.getUserName());
        if (member != null) {
            voteService.delete(id,member);
            return "redirect:/main";
        }
        return null;
    }
    @GetMapping("/item/deleteItem/{id}")
    public String deleteItem(@PathVariable("id")Long id, HttpSession httpSession){
        System.out.println("dasdasdczxcxdnlasndlqwnlkdq");
        Member member = (Member) httpSession.getAttribute("loginMember");

        if (member != null) {

            itemService.deleteItem(id, member);
            return "redirect:/main";
        }
        return null;
    }
    @GetMapping("/vote/deleteItem/{id}")
    public String deleteItemMain(@PathVariable("id")Long id, HttpSession httpSession){
        Member member = (Member) httpSession.getAttribute("loginMember");
        if(member!=null){
            Item item = itemRepository.findById(id).orElse(null);
            return "redirect:/main";
        }
        return null;
    }

    @GetMapping("/vote/search")
    public String searchVotes(@RequestParam String keyword, Model model, HttpSession httpSession) {
        List<Vote> votes = voteRepository.findAllByTitle(keyword);
        Member member = (Member) httpSession.getAttribute("loginMember");
        List<Item> items = itemRepository.findAll();
        List<MemberVote> memberVotes = memberVoteRepository.findAll();
        if (member != null) {

            model.addAttribute("member", member);
            model.addAttribute("votes", votes);
            model.addAttribute("items", items);
            model.addAttribute("memberVotes", memberVotes);
            List<Vote> reVoteableVotes = memberVotes.stream()
                    .filter(memberVote -> memberVote.getMember().getId().equals(member.getId()))
                    .map(MemberVote::getVote)
                    .distinct()
                    .collect(Collectors.toList());

            model.addAttribute("reVoteableVotes", reVoteableVotes);
        }
        return "votelist";
    }

    @GetMapping("main")
    public String list(Model model,HttpSession httpSession){
        List<Vote> votes = voteRepository.findAll();
        System.out.println(votes);
        Member member = (Member) httpSession.getAttribute("loginMember");
        List<Item> items = itemRepository.findAll();
        List<MemberVote> memberVotes = memberVoteRepository.findAll();
        if(member!=null){

            model.addAttribute("member",member);
            model.addAttribute("votes",votes);
            model.addAttribute("items",items);
            model.addAttribute("memberVotes",memberVotes);
            List<Vote> reVoteableVotes = memberVotes.stream()
                    .filter(memberVote -> memberVote.getMember().getId().equals(member.getId()))
                    .map(MemberVote::getVote)
                    .distinct()
                    .collect(Collectors.toList());

            model.addAttribute("reVoteableVotes", reVoteableVotes);
        }

        return "main";
    }
    @GetMapping("/vote/result/{id}")
    public String result(@PathVariable("id") Long id, Model model, HttpSession httpSession) {
        Vote vote = voteService.selectVote(id);
        Member member = (Member) httpSession.getAttribute("loginMember");
        if(member!=null){
            List<Object[]> itemVoteCounts = voteService.countVotesByItemId(id);
            model.addAttribute("vote", vote);
            model.addAttribute("itemVoteCounts", itemVoteCounts);

            return "vote/result";
        }
        return null;
    }
    @PostMapping("/vote/result/{id}")
    public String resultMain(@PathVariable("id")Long id){
        Vote vote = voteRepository.findById(id).orElse(null);
        return "redirect:/main";
    }


    @GetMapping("/vote/vote/{id}")
    public String voteForm(@PathVariable("id")Long id, HttpSession httpSession,Model model){
        Member member = (Member) httpSession.getAttribute("loginMember");

        if(member!=null){

            Vote vote = voteService.selectVote(id);
            MemberVote memberVote = memberVoteRepository.findMemberVoteByVoteAndMember(vote,member);
            model.addAttribute("memberVote",memberVote);
            model.addAttribute("member",member);
            model.addAttribute("vote",vote);
            return "vote/vote";
        }
        return null;
    }
    @PostMapping("/vote/vote/{id}")
    public String vote(@PathVariable("id")Long id,@RequestParam("itemId") Long itemId,HttpSession httpSession){
        Member member =(Member) httpSession.getAttribute("loginMember");

        if(member!=null){
            voteService.selectItem(member,itemId);
            return "redirect:/main";
        }
        return null;
    }

}
