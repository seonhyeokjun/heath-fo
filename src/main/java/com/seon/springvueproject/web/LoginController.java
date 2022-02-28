package com.seon.springvueproject.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class LoginController {
    @GetMapping("/google")
    public String googleLogin() {
        return "redirect:http://ec2-3-38-60-229.ap-northeast-2.compute.amazonaws.com/oauth2/authorization/google";
    }

    @GetMapping("/naver")
    public String naverLogin() {
        return "redirect:/oauth2/authorization/naver";
    }
}