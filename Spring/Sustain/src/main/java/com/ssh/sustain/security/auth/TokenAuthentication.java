package com.ssh.sustain.security.auth;

import com.ssh.sustain.repository.TokenRepository;
import com.ssh.sustain.security.auth.cookie.CookieUtil;
import com.ssh.sustain.security.auth.jwt.JwtUtil;
import com.ssh.sustain.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenAuthentication {

    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final UserService userService;
    private final TokenRepository tokenRepository;

    

}
