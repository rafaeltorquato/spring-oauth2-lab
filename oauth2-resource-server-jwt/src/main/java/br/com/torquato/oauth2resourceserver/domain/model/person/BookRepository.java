package br.com.torquato.oauth2resourceserver.domain.model.person;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Book save(Book book);

    Optional<Book> findById(String id);

    List<Book> listAll();

    void delete(String id);

}
