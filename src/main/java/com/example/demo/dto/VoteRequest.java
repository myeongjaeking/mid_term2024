package com.example.demo.dto;

import com.example.demo.domain.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VoteRequest {
    private List<Item>items;
    private String title;
}
