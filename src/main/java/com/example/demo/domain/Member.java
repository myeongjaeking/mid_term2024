package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
@Column(name="username")
    private String userName;
@Column(name="password")
    private String password;

@OneToMany(mappedBy = "member")
    private List<Vote> votes;
@OneToMany(mappedBy = "member")
    private List<Item> items;
@OneToMany(mappedBy = "member")
    private List<MemberVote> memberVotes;

}
