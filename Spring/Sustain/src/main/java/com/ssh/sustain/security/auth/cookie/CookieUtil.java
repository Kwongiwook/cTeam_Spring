package com.ssh.sustain.security.auth.cookie;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class CookieUtil {

    public Cookie createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        return cookie;
    }

    public Cookie getCookie(HttpServletRequest request, String name) {
        Assert.notNull(request, "HttpServletRequest can't be null");
        Assert.notNull(name, "Cookie Name is can't be null");

        final Cookie[] cookies = request.getCookies();
        return cookies != null ? getCookieByName(cookies, name) : null;
    }

    private Cookie getCookieByName(Cookie[] cookies, String name) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }

}
