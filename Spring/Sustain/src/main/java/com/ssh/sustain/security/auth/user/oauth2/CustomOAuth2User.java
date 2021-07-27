package com.ssh.sustain.security.auth.user.oauth2;

import org.springframework.security.oauth2.core.user.OAuth2User;

public interface CustomOAuth2User extends OAuth2User {

    String getUID();

    String getNickName();

    String getAuth();

}
