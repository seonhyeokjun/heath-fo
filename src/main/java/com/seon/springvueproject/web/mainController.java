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
    public String index(){
        return "redirect:http://localhost:3000";
    }

    @GetMapping("/auth/client")
    @ResponseBody
    public Map<String, Object> client(@LoginUser SessionUser sessionUser){
        Map<String, Object> map = new HashMap<String, Object>();

        if (sessionUser != null){
            map.put("userName", sessionUser.getName());
            map.put("userEmail", sessionUser.getEmail());
            map.put("picture", sessionUser.getPicture());
        }

        return map;
    }
}
