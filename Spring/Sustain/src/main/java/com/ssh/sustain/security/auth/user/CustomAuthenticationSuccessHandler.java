package com.ssh.sustain.security.auth.user;

import com.ssh.sustain.model.user.Token;
import com.ssh.sustain.model.user.User;
import com.ssh.sustain.repository.TokenRepository;
import com.ssh.sustain.security.auth.cookie.CookieUtil;
import com.ssh.sustain.security.auth.jwt.JwtUtil;
import com.ssh.sustain.security.auth.user.oauth2.DefaultCustomOAuth2User;
import com.ssh.sustain.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Log4j2
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    // save or update
    private final UserService userService;

    private final JwtUtil jwtUtil;

    private final TokenRepository tokenRepository;

    private final CookieUtil cookieUtil;

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * @param request        redirect param
     * @param response       setHeader
     * @param authentication getPrincipal
     * @throws ServletException servlet
     * @throws IOException      io
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {
        if (authentication.getPrincipal() instanceof OAuth2User) {
            persistence((DefaultCustomOAuth2User) authentication.getPrincipal());
        }
        request.setAttribute("Authorization", authentication);

        String refreshToken;
        String accessToken;

        if (tokenRepository.existsById(authentication.getName())) {
            refreshToken = tokenRepository.findById(authentication.getName()).get().getRefreshToken();
        } else {
            refreshToken = jwtUtil.generateRefreshToken(authentication);
            tokenRepository.save(new Token(authentication.getName(), refreshToken));
        }
        accessToken = jwtUtil.generateAccessToken(refreshToken);
        response.addCookie(cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, accessToken));

        redirectStrategy.sendRedirect(request, response, "/");
    }

    private void persistence(DefaultCustomOAuth2User customOAuth2User) {
        Assert.notNull(customOAuth2User, "customOAuth2User is null");

        if (!userService.isExists(customOAuth2User.getName())) {
            User user = User.builder()
                    .uid(customOAuth2User.getUID())
                    .email(customOAuth2User.getName())
                    .nickname(customOAuth2User.getNickName())
                    .profile((String) customOAuth2User.getAttributes().get("picture"))
                    .auth(customOAuth2User.getAuth())
                    .build();
            userService.saveSocial(user);
        } else {
            User user = User.builder()
                    .uid(customOAuth2User.getUID())
                    .email(customOAuth2User.getName())
                    .nickname(customOAuth2User.getNickName())
                    .profile((String) customOAuth2User.getAttributes().get("picture"))
                    .auth(customOAuth2User.getAuth())
                    .build();

            userService.update(user);
        }
    }

}
