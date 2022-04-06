package com.seon.springvueproject.web;

import com.seon.springvueproject.config.auth.LoginUser;
import com.seon.springvueproject.config.auth.dto.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class mainController {
    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/side-bar")
    public String sideBar() {
        return "side-bar";
    }
}
