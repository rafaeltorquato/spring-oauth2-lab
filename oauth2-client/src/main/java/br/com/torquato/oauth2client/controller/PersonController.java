package br.com.torquato.oauth2client.controller;

import br.com.torquato.oauth2client.config.AppConfiguration;
import br.com.torquato.oauth2client.controller.dto.PersonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PersonController extends BaseController {

    private final WebClient webClient;
    private final AppConfiguration appConfiguration;

    @GetMapping("/persons")
    public ModelAndView index(@AuthenticationPrincipal OAuth2User oauth2User,
                              @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
        List<PersonDTO> persons = fetchPersons(authorizedClient);
        ModelAndView index = putUserDetails(new ModelAndView("index"), oauth2User);
        index.addObject("persons", persons);
        return index;
    }

    private List<PersonDTO> fetchPersons(OAuth2AuthorizedClient authorizedClient) {
        return this.webClient.get()
                .uri(this.appConfiguration.getPersonsUrl())
                .header("Authorization", extractBearerToken(authorizedClient))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PersonDTO>>() {})
                .block();
    }

}
