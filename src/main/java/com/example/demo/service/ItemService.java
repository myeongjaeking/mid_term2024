package com.example.demo.service;

import com.example.demo.domain.Item;
import com.example.demo.domain.Member;
import com.example.demo.domain.MemberVote;
import com.example.demo.domain.Vote;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.MemberVoteRepository;
import com.example.demo.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final VoteRepository voteRepository;
    private final MemberVoteRepository memberVoteRepository;
    @Transactional
    public void deleteItem(Long id, Member member){
        Item itme = itemRepository.findById(id).orElse(null);
        List<MemberVote> memberVotes = memberVoteRepository.findAllByItem(itme);
        for(MemberVote memberVote : memberVotes){
            memberVoteRepository.delete(memberVote);
        }
        itemRepository.delete(itme);
    }



}
