package com.seon.springvueproject.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/google")
    public String googleLogin() {
        return "redirect:/oauth2/authorization/google";
    }

    @GetMapping("/naver")
    public String naverLogin() {
        return "redirect:/oauth2/authorization/naver";
    }
}