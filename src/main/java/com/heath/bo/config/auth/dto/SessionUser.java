package com.heath.bo.config.auth.dto;

import com.heath.bo.user.domain.model.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
