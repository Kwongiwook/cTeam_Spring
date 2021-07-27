package com.ssh.sustain.security.auth.user.oauth2;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequestEntityConverter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private static final ParameterizedTypeReference<Map<String, Object>> PARAMETERIZED_RESPONSE_TYPE = new ParameterizedTypeReference<Map<String, Object>>() {
    };

    private final Converter<OAuth2UserRequest, RequestEntity<?>> requestEntityConverter = new OAuth2UserRequestEntityConverter();

    public static final String USER_ATTIBUTE_KEY = "email";

    private RestOperations restOperations;

    public CustomOAuth2UserService() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
        this.restOperations = restTemplate;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        Assert.notNull(request, "OAuth2UserRequest can't be null");
        if (!StringUtils.hasText(request.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri())) {
            OAuth2Error oauth2Error = new OAuth2Error("missing_user_info_uri",
                    "Missing required UserInfo Uri in UserInfoEndpoint for Client Registration: " +
                            request.getClientRegistration()
                                    .getRegistrationId(), null);
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
        } else {
            String userNameAttributeKey = request.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
            if (!StringUtils.hasText(userNameAttributeKey)) {
                OAuth2Error oauth2Error = new OAuth2Error("missing_user_name_attribute",
                        "Missing required \"user name\" attribute name in UserInfoEndpoint for Client Registration: " +
                                request.getClientRegistration()
                                        .getRegistrationId(), null);

                throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
            } else {
                RequestEntity requestEntity = (RequestEntity) this.requestEntityConverter.convert(request);
                ResponseEntity responseEntity;
                OAuth2Error oAuth2Error;

                try {
                    assert requestEntity != null;
                    responseEntity = this.restOperations.exchange(requestEntity, PARAMETERIZED_RESPONSE_TYPE);
                } catch (OAuth2AuthenticationException exp1) {
                    oAuth2Error = exp1.getError();

                    StringBuilder errorDetails = new StringBuilder();

                    errorDetails.append("Error details: [");
                    errorDetails.append("Error Code: ").append(oAuth2Error.getErrorCode());

                    if (oAuth2Error.getDescription() != null) {
                        errorDetails.append(", Error Description: ").append(oAuth2Error.getDescription());
                    }
                    errorDetails.append("]");

                    oAuth2Error = new OAuth2Error("invalid_user_info_response",
                            "An error occurred while attempting to retrieve the UserInfo Resource: " +
                                    errorDetails, null);

                    throw new OAuth2AuthenticationException(oAuth2Error, oAuth2Error.toString(), exp1);
                } catch (RestClientException exp2) {
                    oAuth2Error = new OAuth2Error("invalid_user_info_response",
                            "An error occurred while attempting to retrieve the UserInfo Resource: " +
                                    exp2.getMessage(), null);

                    throw new OAuth2AuthenticationException(oAuth2Error, oAuth2Error.toString(), exp2);
                }
                return of(request.getClientRegistration().getRegistrationId(), (Map<String, Object>) responseEntity.getBody());
            }
        }
    }

    private DefaultCustomOAuth2User of(String registrationId, Map<String, Object> attr) {
        return registrationId.equalsIgnoreCase("naver") ?
                ofNaver((Map<String, Object>) attr.get("response"))
                :
                ofGoogle(attr);
    }

    private DefaultCustomOAuth2User ofGoogle(Map<String, Object> attr) {
        return new DefaultCustomOAuth2User(Collections
                .singleton(
                        new OAuth2UserAuthority(attr)
                ),
                attr,
                USER_ATTIBUTE_KEY);
    }

    private DefaultCustomOAuth2User ofNaver(Map<String, Object> attr) {
        Map<String, Object> info = new LinkedHashMap<>();
        info.put("sub", attr.get("id"));
        info.put("name", attr.get("nickname"));
        info.put("picture", attr.get("profile_image"));
        info.put("email", attr.get("email"));

        return new DefaultCustomOAuth2User(Collections
                .singleton(
                        new OAuth2UserAuthority(info)
                ),
                info,
                USER_ATTIBUTE_KEY);
    }

}
