package com.ssh.sustain.dto.user;

import com.ssh.sustain.model.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class ClaimUserDTO {

    private final String email;

    private final String nickname;

    private final String auth;

    @Builder
    public ClaimUserDTO(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.auth = user.getAuth();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("email", this.email);
        map.put("nickname", this.nickname);
        map.put("auth", this.auth);

        return map;
    }
}
