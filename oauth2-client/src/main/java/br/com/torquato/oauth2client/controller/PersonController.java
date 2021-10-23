package br.com.torquato.oauth2client.controller;

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

    @GetMapping("/persons")
    public ModelAndView index(@AuthenticationPrincipal OAuth2User oauth2User,
                              @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
        String token = "Bearer " + authorizedClient.getAccessToken().getTokenValue();
        List<PersonDTO> persons = webClient.get()
                .uri("http://localhost:8181/persons")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PersonDTO>>() {})
                .block();
        System.out.println(persons);
        ModelAndView index = putUserDetails(new ModelAndView("index"), oauth2User);
        index.addObject("persons", persons);
        return index;
    }

}
