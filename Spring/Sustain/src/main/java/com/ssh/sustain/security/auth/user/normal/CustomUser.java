package com.ssh.sustain.security.auth.user.normal;

import com.ssh.sustain.security.auth.user.oauth2.DefaultUserAPI;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.*;

public class CustomUser extends User implements DefaultUserAPI {
    private static final long serialVersionUID = 1L;

    private final Map<String, Object> attributes;
    private final Collection<? extends GrantedAuthority> authority;

    public CustomUser(@NonNull Map<String, Object> attr, Set<SimpleGrantedAuthority> authority) {
        super((String) attr.get("email"), (String) attr.get("pwd"), authority);
        this.attributes = attr;
        this.authority = authority;
    }

    @Override
    public String getAuth() {
        String role = null;
        for (GrantedAuthority authority : this.getAuthority()) {
            role = authority.getAuthority();
        }
        return role;
    }

    public String getName() {
        return (String) this.getAttributes().get("email");
    }

    @Override
    public String getNickName() {
        return (String) this.getAttributes().get("nickname");
    }

    public Collection<? extends GrantedAuthority> getAuthority() {
        return this.authority;
    }

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

}
