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

public class VoteService {
    private final VoteRepository voteRepository;
    private final ItemRepository itemRepository;
    private final MemberVoteRepository memberVoteRepository;
    @Transactional
    public void create(String title, List<String> items, Member member){

        Vote vote = new Vote();
        vote.setMember(member);
        vote.setTitle(title);
        voteRepository.save(vote);

        for(String item : items){
            if(item!=null){
                Item saveItem = new Item();
                saveItem.setContent(item);
                saveItem.setVote(vote);
                saveItem.setMember(member);
                itemRepository.save(saveItem);
            }
        }

    }
    @Transactional
    public void delete(Long id, Member member) {
        Optional<Vote> optionalVote = voteRepository.findById(id);
        if (optionalVote.isPresent()) {
            Vote vote = optionalVote.get();

            for (Item item : vote.getItems()) {
                if (item != null) {
                    List<MemberVote> memberVotes = memberVoteRepository.findAllByItem(item);
                    for (MemberVote memberVote : memberVotes) {
                        memberVoteRepository.delete(memberVote);
                    }

                    itemRepository.delete(item);
                }
            }


            voteRepository.delete(vote);
        }
    }


    public Vote selectVote(Long id){
        return voteRepository.findById(id).orElse(null);}
    @Transactional
    public void selectItem(Member member, Long id){
        Item item = itemRepository.findById(id).orElse(null);
        Vote vote= item.getVote();
        MemberVote memberVotePresent = memberVoteRepository.findMemberVoteByVoteAndMember(vote,member);
        if(memberVotePresent!=null) {
            memberVoteRepository.delete(memberVotePresent);
        }
        MemberVote memberVote = new MemberVote();
        memberVote.setVote(vote);
        memberVote.setMember(member);
        memberVote.setIs_vote(true);

        memberVote.setItem(item);
        memberVoteRepository.save(memberVote);
    }
    public List<Object[]> countVotesByItemId(Long voteId) {
        return memberVoteRepository.countVotesByItemId(voteId);
    }
}
