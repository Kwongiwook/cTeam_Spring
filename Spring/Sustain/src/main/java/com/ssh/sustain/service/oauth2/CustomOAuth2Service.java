package com.ssh.sustain.service.oauth2;

import com.ssh.sustain.domain.UserTest;
import com.ssh.sustain.domain.auth.OAuthAttributes;
import com.ssh.sustain.domain.auth.SessionUser;
import com.ssh.sustain.service.UserTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
@Log4j
public class CustomOAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final HttpSession httpSession;
    private final UserTestService userTestService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, userRequest.getAdditionalParameters());

        UserTest userTest = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(userTest));

        return new DefaultOAuth2User(
                Collections.
                        singleton
                                (new SimpleGrantedAuthority(
                                                userTest.getRoleKey()
                                        )
                                ),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private UserTest saveOrUpdate(@NotNull OAuthAttributes attributes) {
        UserTest userTest = userTestService.findByEmail(attributes.getEmail());

        if (userTest != null) {
            userTestService.update(userTest);
        } else {
            userTest = attributes.toEntity();
            userTestService.save(userTest);
        }
        return userTest;
    }
}
