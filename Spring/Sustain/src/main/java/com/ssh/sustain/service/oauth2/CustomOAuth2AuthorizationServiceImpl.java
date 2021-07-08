package com.ssh.sustain.service.oauth2;

import com.ssh.sustain.security.oauth2.client.CustomOAuth2AuthorizationRequestRedirect;
import com.ssh.sustain.security.oauth2.client.rest.CustomOAuth2UserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponse;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author SmolKim
 * @version 0.0.0 version
 */

@Log4j
@Service
@DependsOn(value = {"repository"})
@RequiredArgsConstructor
public class CustomOAuth2AuthorizationServiceImpl {

    private final CustomOAuth2UserDetails customOAuth2UserDetails;

    private final CustomOAuth2Service customOAuth2Service;

    @Resource(name = "repository")
    private ClientRegistrationRepository clientRegistrationRepository;

    private AuthorizationRequestRepository<OAuth2AuthorizationRequest> requestRepository;

    private OAuth2AuthorizationExchange authorizationExchange;

    private OAuth2AccessTokenResponse oAuth2AccessTokenResponse;

    private String registration;

    @PostConstruct
    private void init() {
        this.requestRepository = new HttpSessionOAuth2AuthorizationRequestRepository();
    }

    /**
     * @param request        sendRedirectForAuthorization
     * @param response       sendRedirectForAuthorization
     * @param registrationId this.registrationId
     * @throws IOException redirectStrategy.sendRedirect
     */
    public void customRequestRedirect(HttpServletRequest request, HttpServletResponse response, String registrationId) throws IOException {
        CustomOAuth2AuthorizationRequestRedirect customOAuth2AuthorizationRequestRedirect = new CustomOAuth2AuthorizationRequestRedirect
                (clientRegistrationRepository, requestRepository);
        this.registration = registrationId;
        log.info("registrationId : " + this.registration);

        customOAuth2AuthorizationRequestRedirect.requestResolve(request, response);
    }

    /**
     * @param request  getParameter("code")
     * @param response 아직 어떻게 쓸지 모르지만 받아만 둠.
     */
    public void customOAuthAuthorizationProcess(HttpServletRequest request, HttpServletResponse response) {
        this.authorizationExchange = oAuth2AuthorizationExchange(
                oAuth2AuthorizationRequest(request)
                , oAuth2AuthorizationResponse(request));

        this.oAuth2AccessTokenResponse = this.customOAuth2UserDetails
                .getOAuth2AccessToken(
                        clientRegistrationRepository
                                .findByRegistrationId(this.registration)
                        , authorizationExchange
                                .getAuthorizationResponse());

        if (oAuth2AccessTokenResponse != null) {
            customOAuth2UserInfo();
        }
    }

    /**
     * RestTemplate to 'UserInfoUri' create OAuth2UserRequest(return value)
     */
    private void customOAuth2UserInfo() {
        OAuth2UserRequest oAuth2UserRequest = this.customOAuth2UserDetails
                .getOAuth2UserInfo(
                        oAuth2AccessTokenResponse,
                        this.clientRegistrationRepository.findByRegistrationId(this.registration));

        if (oAuth2UserRequest != null) {
            log.info(oAuth2UserRequest.getAdditionalParameters().get("id"));

            this.loadUser(oAuth2UserRequest);
        }
    }

    private void loadUser(OAuth2UserRequest request) {
        this.customOAuth2Service.loadUser(request);
    }

    /**
     * @param request current HttpServletRequest로 lordAuthorizationRequest 추출
     * @return this.requestRepository.loadAuthorizationRequest(request)
     */
    private OAuth2AuthorizationRequest oAuth2AuthorizationRequest(HttpServletRequest request) {
        return this.requestRepository.loadAuthorizationRequest(request);
    }

    /**
     * @param request Param("code") != null ? success().build : error().build
     * @return OAuth2AuthorizationResponse.success
     */
    private OAuth2AuthorizationResponse oAuth2AuthorizationResponse(HttpServletRequest request) {
        return request.getParameter("code") != null ?
                OAuth2AuthorizationResponse.success(
                        request.getParameter("code"))
                        .redirectUri(
                                this.clientRegistrationRepository
                                        .findByRegistrationId(this.registration)
                                        .getRedirectUri()
                        ).build()

                : OAuth2AuthorizationResponse.error(
                request.getParameter("errorCode"))
                .redirectUri(
                        this.clientRegistrationRepository
                                .findByRegistrationId(this.registration)
                                .getRedirectUri()).build();
    }

    /**
     * @param request  OAuth2AuthorizationRequest
     * @param response OAuth2AuthorizationResponse
     * @return new OAuth2AuthorizationExchange(request, response)
     */
    private OAuth2AuthorizationExchange oAuth2AuthorizationExchange(OAuth2AuthorizationRequest request, OAuth2AuthorizationResponse response) {
        return new OAuth2AuthorizationExchange(request, response);
    }

}
