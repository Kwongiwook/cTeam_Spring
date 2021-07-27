package com.ssh.sustain.security.auth.jwt;

import com.ssh.sustain.dto.user.ClaimUserDTO;
import com.ssh.sustain.security.auth.user.oauth2.CustomOAuth2UserService;
import com.ssh.sustain.security.auth.user.oauth2.DefaultCustomOAuth2User;
import com.ssh.sustain.service.user.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Log4j2
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Autowired
    private UserService userService;

    // 30m (3,600,000 Miliseconds)
    public final static long ACCESS_TOKEN_VALIDATE_SECOND = 60 * 60 * 1000L;

    // 4wk (24,192,00,000 Miliseconds)
    public final static long REFRESH_TOKEN_VALIDATE_SECOND = 60 * 60 * 24 * 28 * 1000L;

    public final static String ACCESS_TOKEN_NAME = "accessToken";

    @NonNull
    private Key generateKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(SECRET_KEY));
    }

    public String generateRefreshToken(Authentication authentication) {
        return authentication.getPrincipal() instanceof OAuth2User ?
                generateToken(generateClaims(authentication.getName(), "social"), REFRESH_TOKEN_VALIDATE_SECOND)
                :
                generateToken(generateClaims(authentication.getName(), "user"), REFRESH_TOKEN_VALIDATE_SECOND);
    }

    public String generateRefreshToken(String refreshToken) {
        return validateToken(refreshToken) ? generateToken(parseToken(refreshToken), REFRESH_TOKEN_VALIDATE_SECOND)
                :
                null;
    }

    public String generateAccessToken(String refreshToken) {
        return validateToken(refreshToken) ?
                generateToken(parseToken(refreshToken), ACCESS_TOKEN_VALIDATE_SECOND)
                :
                generateRefreshToken(refreshToken);
    }

    private Claims generateClaims(String name, String type) {
        Map<String, Object> userMap = ClaimUserDTO.builder()
                .user(userService.findByEmail(name))
                .build().toMap();
        Claims claims = Jwts.claims().setSubject(name);
        claims.put("type", type);
        claims.put("attr", userMap);

        return claims;
    }

    private String generateToken(Claims claims, long second) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + second))
                .signWith(generateKey())
                .compact();
    }

    public Authentication getAuthentication(String jwt) {
        Claims claims = parseToken(jwt);
        if (claims.get("type").equals("social")) {
            Map<String, Object> attr = (Map<String, Object>) claims.get("attr");
            Set<GrantedAuthority> authorities = Collections.singleton(new OAuth2UserAuthority(attr));
            DefaultCustomOAuth2User user = new DefaultCustomOAuth2User(authorities, attr, CustomOAuth2UserService.USER_ATTIBUTE_KEY);
            return new OAuth2AuthenticationToken(user, user.getAuthorities(), user.getName());
        }
        return null;
    }

    public String getEmail(String token) {
        Map<String, Object> attr = (Map<String, Object>) parseToken(token).get("attr");
        return (String) attr.get("email");
    }

    private Claims parseToken(String token) {
        return validateToken(token) ?
                Jwts.parserBuilder().setSigningKey(generateKey()).build().parseClaimsJws(token).getBody()
                :
                null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(generateKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.info("Invalid Token : " + e.getMessage());
        }
        return false;
    }

}
