package com.seon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Collections;

@Controller
public class mainController {

    @GetMapping("/api")
    public String main(){
        return "Hello World";
    }
}
