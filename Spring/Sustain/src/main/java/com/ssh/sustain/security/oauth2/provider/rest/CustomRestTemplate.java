package com.ssh.sustain.security.oauth2.provider.rest;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

@Component
@DependsOn(value = "repository")
public class CustomRestTemplate {

    /**
     * <h2>Naver Token Request Params</h2>
     * <ul>
     *     <li>grant_type : String, *, authorization_code/refresh_token/delete</li>
     *     <li>client_id : String, *</li>
     *     <li>client_secret : String, *</li>
     *     <li>code : String, 발급 시 필수</li>
     *     <li>state : String, 발급 시 필수</li>
     *     <li>refresh_token : String, 갱신 시 필수</li>
     *     <li>access_token : String, 삭제 시 필수</li>
     *     <li>service_provider : String, 삭제 시 필수, 'NAVER'</li>
     * </ul>
     */

    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> restRequest(HttpEntity<Map<String, String>> entity, String uri) {
        return restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
    }

}
