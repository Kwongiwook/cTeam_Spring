package com.ssh.sustain.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
public class UserTest {

    private int id;
    private String name;
    private String email;
    private String picture;
    private Role role;

    @Builder
    public UserTest(int id, String name, String email, String picture, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    @Builder
    public UserTest(int id, String name, String email, String picture, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = Role.valueOf(role.substring(role.indexOf("_") + 1));
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
