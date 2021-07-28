package com.ssh.sustain.dto.user;

import com.ssh.sustain.security.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RegisterUserDTO {

    private final String email;

    private final String nickname;

    private String pwd;

    private final String auth = Role.USER.getRole();

    @Builder
    public RegisterUserDTO(String email, String nickname, String pwd) {
        this.email = email;
        this.nickname = nickname;
        this.pwd = pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
