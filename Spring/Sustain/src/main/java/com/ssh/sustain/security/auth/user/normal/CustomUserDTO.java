package com.ssh.sustain.security.auth.user.normal;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.*;

public class CustomUserDTO extends User {
    private static final long serialVersionUID = 1L;

    private Map<String, Object> attributes;
    private Set<GrantedAuthority> authorities;
    private String nameAttributeKey;

    public CustomUserDTO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public String getAuth() {
        String role = null;
        for (GrantedAuthority authority : this.getAuthorities()) {
            role = authority.getAuthority();
        }
        return role;
    }

}
