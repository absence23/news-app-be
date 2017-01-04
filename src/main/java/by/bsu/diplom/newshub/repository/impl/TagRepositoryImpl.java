package by.bsu.diplom.newshub.repository.impl;

import by.bsu.diplom.newshub.domain.entity.Tag;
import by.bsu.diplom.newshub.repository.TagRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TagRepositoryImpl implements TagRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tag findById(long id) {
        return entityManager.find(Tag.class, id);
    }

    @Override
    public void delete(Tag tag) {
        entityManager.remove(tag);
    }

    @Override
    public void create(Tag tag) {
        entityManager.persist(tag);
        entityManager.flush();
    }

    @Override
    public Tag update(Tag tag) {
        return entityManager.merge(tag);
    }

    @Override
    public Map<Tag, Long> findAllWithNewsCount() {
        List<Object[]> objects = entityManager.createNamedQuery("Tag.findWithCount").getResultList();
        Map<Tag, Long> tags = new HashMap<>();
        objects.forEach((array -> tags.put((Tag) array[0], (long) array[1])));
        return tags;
    }
}
