package com.ssh.sustain.controller.auth;

import com.ssh.sustain.repository.TokenRepository;
import com.ssh.sustain.security.auth.cookie.CookieUtil;
import com.ssh.sustain.security.auth.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@Log4j2
@RequiredArgsConstructor
@Controller
public class AuthController {

    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;
    private final CookieUtil cookieUtil;

    @GetMapping(value = "/register")
    public String toRegister() {
        return "/auth/register";
    }

    @PostMapping(value = "/register")
    public void register() {

    }

    // 로그인이 되면 애초에 로그인 버튼이 안보이게 할 것이지만 일단 선언만 해둠.
    @PreAuthorize(value = "isAnonymous()")
    @GetMapping(value = "/login")
    public String toLogin() {
        return "/auth/login";
    }

    @PostMapping(value = "/login")
    public void login() {

    }

}
