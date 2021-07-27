package com.ssh.sustain.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j2
public class JwtTest {

    private String secretkey = "Ui--hMarda1j9U4ytzphd0iDMOy85MrFk_xGGtgq_Hs";

    Key key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretkey));

    // 30m (1,800,000 Miliseconds)
    private long ACCESS_TOKEN_VALIDATE_TIME = 30 * 60 * 1000L;

    @Before
    public void init() {
        secretkey = Base64.getEncoder().encodeToString(secretkey.getBytes());
    }

    @Test
    public void builderTest() {
        Claims claims = Jwts.claims().setSubject("test");
        claims.put("email", "test@gmail.com");
        claims.put("auth", "ROLE_USER");
        claims.put("nick", "nickname");
        Date date = new Date();

        String compact = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_VALIDATE_TIME))
                .signWith(key)
                .compact();

        log.info("token : " + compact);
        validateToken(compact);

        /*
        {
            "sub": "test",
            "email": "test@gmail.com",
            "auth": "ROLE_USER",
            "nick": "nickname",
            "iat": 1626661751,
            "exp": 1626663551
        }
         */
    }

    public boolean validateToken(String token) {
        log.info("Check Validate of : " + token);
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.info("Invalid Token : " + e.getMessage());
        }
        return false;
    }


}
