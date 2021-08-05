package com.ssh.sustain.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import org.springframework.data.annotation.Id;

@AllArgsConstructor
@Getter
@Setter
@RedisHash(value = "verify")
public class Verify {

    @Id
    private String email;

    private String code;

    @TimeToLive
    private Long expiration;

}
