package com.seon.springvueproject.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class account {

    private final String name;
    private final int amount;

}
