package com.seon.springvueproject.web;

import com.seon.springvueproject.config.auth.LoginUser;
import com.seon.springvueproject.config.auth.dto.SessionUser;
import com.seon.springvueproject.web.dto.account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class mainController {
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser sessionUser){
        if (sessionUser != null){
            model.addAttribute("userName", sessionUser.getName());
        }

        return "redirect:http://localhost:3000";
    }

    @GetMapping("/hello")
    public String main(){
        return "Hello World";
    }

    @GetMapping("/hello/dto")
    public account helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount){
        return new account(name, amount);
    }
}
