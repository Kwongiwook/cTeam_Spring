package com.ssh.sustain.security.oauth2.client;

import lombok.extern.log4j.Log4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;

@Log4j
public class CustomOAuth2AuthorizationRequestRedirect {

    public final static String DEFAULT_AUTHORIZATION_REQUEST_URI = "/oauth2/authorization";

    private final OAuth2AuthorizationRequestResolver requestResolver;

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private final AuthorizationRequestRepository<OAuth2AuthorizationRequest> requestRepository;

    public CustomOAuth2AuthorizationRequestRedirect(ClientRegistrationRepository clientRegistrationRepository
            , AuthorizationRequestRepository<OAuth2AuthorizationRequest> requestRepository) {
        Assert.notNull(clientRegistrationRepository, "ClientRegistrationRepository can not be empty");
        Assert.notNull(requestRepository, "AuthorizationRequestRepository can not be empty");

        this.requestRepository = requestRepository;
        this.requestResolver = new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository
                , DEFAULT_AUTHORIZATION_REQUEST_URI);
    }

    public void requestResolve(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            OAuth2AuthorizationRequest authorizationRequest = this.requestResolver.resolve(request);

            if (authorizationRequest != null) {
                sendRedirectForAuthorization(request, response, authorizationRequest);
            }
        } catch (IOException exception) {
            unsuccessufulAuthorizationRequestRedirect(request, response, exception);
        }
    }

    private void sendRedirectForAuthorization(HttpServletRequest request, HttpServletResponse response
            , @NotNull OAuth2AuthorizationRequest authorizationRequest) throws IOException {
        log.info("sendRedirectForAuthorization : " + authorizationRequest.getAuthorizationRequestUri());

        if (AuthorizationGrantType.AUTHORIZATION_CODE.equals(authorizationRequest.getGrantType())) {
            this.requestRepository.saveAuthorizationRequest(authorizationRequest, request, response);
        }
        this.redirectStrategy.sendRedirect(request, response, authorizationRequest.getAuthorizationRequestUri());
    }

    private void unsuccessufulAuthorizationRequestRedirect(HttpServletRequest request, @NotNull HttpServletResponse response,
                                                           Exception exception) throws IOException {
        log.error(MessageFormat.format("Authorization Request is failed : %s", exception, exception));
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }
}
