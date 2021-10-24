package br.com.torquato.oauth2client.controller;

import br.com.torquato.oauth2client.config.AppConfiguration;
import br.com.torquato.oauth2client.controller.dto.BookDTO;
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
public class BookController extends BaseController {

    private final WebClient webClient;
    private final AppConfiguration appConfiguration;

    @GetMapping("/books")
    public ModelAndView index(@AuthenticationPrincipal OAuth2User oauth2User,
                              @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
        String token = extractBearerToken(authorizedClient);
        List<BookDTO> books = this.webClient.get()
                .uri(this.appConfiguration.getBooksUrl())
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<BookDTO>>() {})
                .block();
        ModelAndView index = putUserDetails(new ModelAndView("index"), oauth2User);
        index.addObject("books", books);
        return index;
    }

}
