package br.com.torquato.oauth2client.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//Should be refresh scope
@Component
@ConfigurationProperties(prefix = "app.urls")
@Getter
@Setter
public class AppConfiguration {

    private String booksUrl;
    private String personsUrl;
    private String logoutSuccessUrl;

}
