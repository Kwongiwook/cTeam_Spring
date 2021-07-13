package com.ssh.sustain.model.security;

import com.ssh.sustain.model.UserTest;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(UserTest userTest) {
        this.name = userTest.getName();
        this.email = userTest.getEmail();
        this.picture = userTest.getPicture();
    }
}