package br.com.torquato.oauth2resourceserver.domain.model.person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {

    Person save(Person person);

    Optional<Person> findById(String id);

    List<Person> listAll();

    void delete(String id);

}
