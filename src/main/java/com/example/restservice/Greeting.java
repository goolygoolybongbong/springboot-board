package com.example.restservice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Greeting {
    private final long id;
    private final String content;
}
