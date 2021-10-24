package br.com.torquato.oauth2resourceserver.delivery.rest;

import br.com.torquato.oauth2resourceserver.domain.model.person.Book;
import br.com.torquato.oauth2resourceserver.domain.model.person.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookRestController {

    private final BookRepository bookRepository;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_view_books')")
    public Book get(@PathVariable String id) {
        return bookRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_view_books')")
    public List<Book> get() {
        return bookRepository.listAll();
    }

}
