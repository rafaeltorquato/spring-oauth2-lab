package br.com.torquato.oauth2resourceserver.delivery.rest;

import br.com.torquato.oauth2resourceserver.domain.model.person.Person;
import br.com.torquato.oauth2resourceserver.domain.model.person.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonRestController {

    private final PersonRepository personRepository;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('SCOPE_view_persons')")
    public Person get(@PathVariable String id) {
        return personRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('SCOPE_view_persons')")
    public List<Person> get() {
        return personRepository.listAll();
    }

}
