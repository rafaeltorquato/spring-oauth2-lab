package br.com.torquato.oauth2resourceserver.infrastructure.repository;

import br.com.torquato.oauth2resourceserver.domain.model.person.Person;
import br.com.torquato.oauth2resourceserver.domain.model.person.PersonRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MapPersonRepository implements PersonRepository {

    private static final Map<String, Person> map = new ConcurrentHashMap<>();

    static {
        Person person = new Person(
                UUID.randomUUID().toString(),
                "João da Silva",
                (short) 55
        );
        map.put(person.getId(), person);
        person = new Person(
                UUID.randomUUID().toString(),
                "Márcio Vitor",
                (short) 40
        );
        map.put(person.getId(), person);
    }

    @Override
    public Person save(Person person) {
        if (person.getId() == null) {
            person.setId(UUID.randomUUID().toString());
        }
        map.put(person.getId(), person);

        return person;
    }

    @Override
    public Optional<Person> findById(String id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public List<Person> listAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public void delete(String id) {
        map.remove(id);
    }

}
