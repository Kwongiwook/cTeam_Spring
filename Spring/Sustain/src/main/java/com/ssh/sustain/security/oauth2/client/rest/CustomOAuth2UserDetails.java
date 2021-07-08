package com.ssh.sustain.security.oauth2.client.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssh.sustain.security.oauth2.provider.rest.CustomRestTemplate;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Log4j
@Component
public class CustomOAuth2UserDetails {

    @Resource
    private CustomRestTemplate restTemplate;

    public OAuth2AccessTokenResponse getOAuth2AccessToken(ClientRegistration clientRegistration, OAuth2AuthorizationResponse oAuth2AuthorizationResponse) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "*/*");
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(headers);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(clientRegistration.getProviderDetails().getTokenUri())
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", clientRegistration.getClientId())
                .queryParam("client_secret", clientRegistration.getClientSecret())
                .queryParam("code", oAuth2AuthorizationResponse.getCode());

        return parshingOAuth2AccessToken(
                restTemplate
                        .restRequest(entity, uriComponentsBuilder
                                .toUriString()).getBody());
    }

    public OAuth2UserRequest getOAuth2UserInfo(OAuth2AccessTokenResponse tokenResponse, ClientRegistration clientRegistration) {
        OAuth2AccessToken accessToken = tokenResponse.getAccessToken();
        log.info("accessToken Type : " + accessToken.getTokenType().getValue());
        log.info("accessToken Value : " + accessToken.getTokenValue());

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken.getTokenValue());

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(headers);

        return new OAuth2UserRequest(clientRegistration,
                tokenResponse.getAccessToken(),

                setAdditionalParmas(
                        restTemplate
                                .restRequest
                                        (entity, clientRegistration
                                                .getProviderDetails()
                                                .getUserInfoEndpoint()
                                                .getUri()).getBody())
        );
    }

    private Map<String, Object> setAdditionalParmas(String body) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = objectMapper.readTree(body);

            if (node.get("response").isNull()) {
                return parshingUserInfoGoogle(node);
            } else {
                node = node.get("response");
                return parshingUserInfoNaver(node);
            }
        } catch (IOException exception) {
            return null;
        }
    }

    private Map<String, Object> parshingUserInfoNaver(JsonNode node) {
        Map<String, Object> additionalParameters = new LinkedHashMap<>();
        additionalParameters.put("id", node.get("id").asText());
        additionalParameters.put("nickname", node.get("nickname").asText());
        additionalParameters.put("profile_image", node.get("profile_image").asText());
        additionalParameters.put("email", node.get("email").asText());
        additionalParameters.put("name", node.get("nickname").asText());
        additionalParameters.put("response","What is this?");

        return additionalParameters;
    }

    private Map<String, Object> parshingUserInfoGoogle(JsonNode node) {
        Map<String, Object> additionalParameters = new HashMap<>();
        additionalParameters.put("id", node.get("id").asText());
        additionalParameters.put("email", node.get("email").asText());
        additionalParameters.put("picture", node.get("picture").asText());

        return additionalParameters;
    }

    private OAuth2AccessTokenResponse parshingOAuth2AccessToken(String body) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(body);

            return OAuth2AccessTokenResponse
                    .withToken(rootNode.get("access_token").asText())
                    .tokenType(OAuth2AccessToken.TokenType.BEARER)
                    .refreshToken(rootNode.get("refresh_token").asText())
                    .expiresIn(rootNode.get("expires_in").asLong())
                    .build();
        } catch (IOException exception) {
            return null;
        }
    }
}
