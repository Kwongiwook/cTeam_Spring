package com.ssh.sustain.model.user;

import lombok.Builder;
import lombok.Getter;

import java.sql.Date;

@Getter
public class User {

    // auto_increment, pk
    private Integer id;

    // nullable, unique
    private final String uid;

    // not null, unique
    private final String email;

    // not null
    private final String nickname;

    // nullable
    private final String profile;

    // not null
    private final String auth;

    // default current_timestamp()
    private Date reg_date;

    @Builder
    public User(String uid, String email, String nickname, String profile, String auth) {
        this.uid = uid;
        this.email = email;
        this.nickname = nickname;
        this.profile = profile;
        this.auth = auth;
    }
}
