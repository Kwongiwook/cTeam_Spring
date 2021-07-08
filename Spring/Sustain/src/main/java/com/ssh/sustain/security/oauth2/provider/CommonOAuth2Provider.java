package com.ssh.sustain.security.oauth2.provider;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;

public enum CommonOAuth2Provider {

    NAVER {
        @Override
        public ClientRegistration.Builder getBuilder(String registrationId) {
            return getBuilder(registrationId, ClientAuthenticationMethod.CLIENT_SECRET_POST, DEFAULT_LOGIN_REDIRECT_URL)
                    .scope("profile")
                    .authorizationUri("https://nid.naver.com/oauth2.0/authorize")
                    .tokenUri("https://nid.naver.com/oauth2.0/token")
                    .userInfoUri("https://openapi.naver.com/v1/nid/me")
                    .userNameAttributeName("response")
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .clientName("naver");
        }
    },

    GOOGLE {
        @Override
        public ClientRegistration.Builder getBuilder(String registrationId) {
            return getBuilder(registrationId, ClientAuthenticationMethod.CLIENT_SECRET_POST, DEFAULT_LOGIN_REDIRECT_URL)
                    .scope("https://www.googleapis.com/auth/userinfo.email")
                    .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .tokenUri("https://oauth2.googleapis.com/token")
                    .userInfoUri("https://www.googleapis.com/oauth2/v1/userinfo")
                    .userNameAttributeName(IdTokenClaimNames.SUB)
                    .clientName("google");
        }
    };
    private static final String DEFAULT_LOGIN_REDIRECT_URL = "{baseUrl}/login/oauth2/code/{registrationId}";

    protected final ClientRegistration.Builder getBuilder(String registrationId, ClientAuthenticationMethod method, String redirectUrl) {
        return ClientRegistration.withRegistrationId(registrationId)
                .clientAuthenticationMethod(method)
                .redirectUri(redirectUrl);
    }

    public abstract ClientRegistration.Builder getBuilder(String registrationId);
}
