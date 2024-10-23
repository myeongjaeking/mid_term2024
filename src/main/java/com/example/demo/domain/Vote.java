package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "vote")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "vote",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;

    @Column(name = "title")
    private String title;

    @Column(name="is_vote")
    private boolean is_vote;
    @OneToMany(mappedBy = "vote")
    private List<MemberVote> memberVotes;

}
