package com.example.demo.repository;

import com.example.demo.domain.Item;
import com.example.demo.domain.Member;
import com.example.demo.domain.MemberVote;
import com.example.demo.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberVoteRepository extends JpaRepository<MemberVote,Long> {
    MemberVote findMemberVoteByVoteAndMember(Vote vote, Member member);

    @Query("SELECT i.id, i.content, COUNT(mv) " +
            "FROM Item i " +
            "LEFT JOIN MemberVote mv ON mv.item.id = i.id AND mv.vote.id = :voteId " +
            "WHERE i.vote.id = :voteId " +
            "GROUP BY i.id, i.content")
    List<Object[]> countVotesByItemId(@Param("voteId") Long voteId);


    List<MemberVote> findAllByItem(Item item);

    List<MemberVote> findByMember(Member member);
}
