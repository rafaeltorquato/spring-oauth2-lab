package br.com.torquato.oauth2resourceserver.infrastructure.repository;

import br.com.torquato.oauth2resourceserver.domain.model.person.Book;
import br.com.torquato.oauth2resourceserver.domain.model.person.BookRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MapBookRepository implements BookRepository {

    private static final Map<String, Book> map = new ConcurrentHashMap<>();

    static {
        Book book = new Book(
                UUID.randomUUID().toString(),
                "Lord of The Rings"
        );
        map.put(book.getId(), book);
        book = new Book(
                UUID.randomUUID().toString(),
                "Holy Bible"
        );
        map.put(book.getId(), book);
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(UUID.randomUUID().toString());
        }
        map.put(book.getId(), book);

        return book;
    }

    @Override
    public Optional<Book> findById(String id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public List<Book> listAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public void delete(String id) {
        map.remove(id);
    }

}
