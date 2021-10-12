package com.seon.springvueproject.web;

import com.seon.springvueproject.web.dto.account;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class mainController {
    @GetMapping("/hello")
    public String main(){
        return "Hello World";
    }

    @GetMapping("/hello/dto")
    public account helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount){
        return new account(name, amount);
    }
}
