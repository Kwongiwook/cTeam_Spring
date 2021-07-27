package com.ssh.sustain.dto.user;

import com.ssh.sustain.model.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NormalUserDTO {

    private final String email;

    private final String pwd;

    private final String nickname;

    private final String auth;

    @Builder
    public NormalUserDTO(User user) {
        this.email = user.getEmail();
        this.pwd = user.getPwd();
        this.nickname = user.getNickname();
        this.auth = user.getAuth();
    }
}
