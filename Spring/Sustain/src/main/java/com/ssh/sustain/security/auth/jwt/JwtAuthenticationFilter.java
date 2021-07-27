package com.ssh.sustain.security.auth.jwt;


import com.ssh.sustain.repository.TokenRepository;
import com.ssh.sustain.security.auth.cookie.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final Cookie jwt = cookieUtil.getCookie(httpServletRequest, JwtUtil.ACCESS_TOKEN_NAME);

        if (jwt != null) {
            if (jwtUtil.validateToken(jwt.getValue())) {
                SecurityContextHolder.getContext().setAuthentication(jwtUtil.getAuthentication(jwt.getValue()));
                log.info("SecurityContext.setAuthentication()");
            } else {
                String reissueAccessToken = jwtUtil.generateAccessToken(
                        tokenRepository.findById(jwtUtil.getEmail(jwt.getValue()))
                                .get().getRefreshToken());
                if (jwtUtil.validateToken(reissueAccessToken)) {
                    cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, reissueAccessToken);
                    SecurityContextHolder.getContext().setAuthentication(jwtUtil.getAuthentication(reissueAccessToken));

                    log.info("(Re) SecurityContext.setAuthentication()");
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }


}
