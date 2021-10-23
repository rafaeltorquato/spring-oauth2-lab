package br.com.torquato.oauth2client.controller;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.servlet.ModelAndView;

abstract class BaseController {

    protected ModelAndView putUserDetails(ModelAndView modelAndView, OAuth2User user) {
        modelAndView.addObject("userFullName", user.getName());
        return modelAndView;
    }

    protected String extractBearerToken(OAuth2AuthorizedClient authorizedClient) {
        return "Bearer " + authorizedClient.getAccessToken().getTokenValue();
    }

}
