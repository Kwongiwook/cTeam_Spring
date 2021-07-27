package com.ssh.sustain.model.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@AllArgsConstructor
@Getter
@Setter
@RedisHash("account")
public class Token {

    @Id
    private String email;

    private String refreshToken;

}
