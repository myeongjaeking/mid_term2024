package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "vote_id")
    private Vote vote;

    @Column(name = "content")
    private String content;

    @Column(name = "is_vote")
    private boolean isVote = false;

    @OneToMany(mappedBy = "item")
    private List<MemberVote> memberVotes;

    @Column(name = "count")
    private int count=0;
}
