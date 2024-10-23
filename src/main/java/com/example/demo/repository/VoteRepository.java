package com.example.demo.repository;

import com.example.demo.domain.Item;
import com.example.demo.domain.Member;
import com.example.demo.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote,Long> {

    @Query("SELECT v FROM Vote v WHERE v.title LIKE %:title%")
    List<Vote> findAllByTitle(String title);

}
