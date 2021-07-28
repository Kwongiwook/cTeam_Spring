package com.ssh.sustain.dto.user;

import com.ssh.sustain.model.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

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

    public NormalUserDTO(String email, String pwd, String nickname, String auth) {
        this.email = email;
        this.pwd = pwd;
        this.nickname = nickname;
        this.auth = auth;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("email", this.email);
        map.put("pwd", this.pwd);
        map.put("nickname", this.nickname);
        map.put("auth", this.auth);

        return map;
    }

    public Map<String, Object> toClaimsMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("email", this.email);
        map.put("nickname", this.nickname);
        map.put("auth", this.auth);

        return map;
    }
}
