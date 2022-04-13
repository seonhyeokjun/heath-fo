package com.seon.springvueproject.web.user;

import com.seon.springvueproject.config.auth.LoginUser;
import com.seon.springvueproject.config.auth.dto.SessionUser;
import com.seon.springvueproject.service.user.UserService;
import com.seon.springvueproject.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public Long signUp(@RequestBody UserDto userDto) {
        return userService.save(userDto);
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
