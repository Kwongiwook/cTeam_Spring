package com.ssh.sustain.security;

import com.ssh.sustain.model.UserTest;
import com.ssh.sustain.model.security.OAuthAttributes;
import com.ssh.sustain.model.security.SessionUser;
import com.ssh.sustain.repository.UserTestRepository;
import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserTestRepository repository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> deleage = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = deleage.loadUser(request);

        String registrationId = request.getClientRegistration().getRegistrationId();
        String userNameAttributeName = request.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        UserTest userTest = saveOrUpdate(attributes);

        httpSession.setAttribute("user", new SessionUser(userTest));

        return new DefaultOAuth2User(
                Collections.singleton(
                        new SimpleGrantedAuthority(userTest.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    /**
     * @param attributes loaduer를 통해 생성된 OAuthAtrributes를 parameter로 사용.
     * @return return repository.save()
     */
    private UserTest saveOrUpdate(OAuthAttributes attributes) {
        /*
        Optional<U> map(Function<? super T, ? extends U> mapper)
        entity.update()의 return이 U가 되므로 UserTest가 리턴타입이다.

        그렇기에 userTest에는 UsetTest가 저장되고, return repository.save()의 parameter로 들어갈 수 있다.
         */
        UserTest userTest = repository.findByEmail(attributes.getEmail())
                .map(entity -> entity
                        .update(attributes.getName(),
                                attributes.getPicture()))
                .orElse(attributes.toEntity());

        return repository.save(userTest);
    }
}
