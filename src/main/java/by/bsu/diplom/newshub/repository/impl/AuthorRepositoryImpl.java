package by.bsu.diplom.newshub.repository.impl;

import by.bsu.diplom.newshub.domain.entity.Author;
import by.bsu.diplom.newshub.repository.AuthorRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Author findById(long id) {
        return entityManager.find(Author.class, id);
    }

    @Override
    public void delete(Author author) {
        entityManager.remove(author);
    }

    @Override
    public void create(Author author) {
        entityManager.persist(author);
        entityManager.flush();
    }

    @Override
    public Author update(Author author) {
        return entityManager.merge(author);
    }

    @Override
    public Map<Author, Long> findAllWithNewsCount() {
        List<Object[]> objects = entityManager.createNamedQuery("Author.findWithCount").getResultList();
        Map<Author, Long> authors = new HashMap<>();
        objects.forEach((array -> authors.put((Author) array[0], (long) array[1])));
        return authors;
    }
}
