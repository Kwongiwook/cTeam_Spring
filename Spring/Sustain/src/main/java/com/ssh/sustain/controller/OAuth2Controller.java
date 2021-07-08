package com.ssh.sustain.controller;

import com.ssh.sustain.service.oauth2.CustomOAuth2AuthorizationServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
@Log4j
public class OAuth2Controller {

    private final CustomOAuth2AuthorizationServiceImpl customOAuth2AuthorizationService;

    @GetMapping("/oauth2/authorization/{registrationId}")
    public void oauth2Authorization(HttpServletRequest request, HttpServletResponse response,
                                    @PathVariable("registrationId") String registrationId) throws Exception {
        customOAuth2AuthorizationService.customRequestRedirect(request,response,registrationId);
    }

    @GetMapping("/login/oauth2/code/**")
    public String oauth2Redirect(HttpServletRequest request, HttpServletResponse response) {
        customOAuth2AuthorizationService.customOAuthAuthorizationProcess(request,response);

        return "thymeleaf/welcome";
    }
}
