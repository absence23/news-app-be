package by.bsu.diplom.newshub.repository;

import by.bsu.diplom.newshub.domain.entity.Tag;

import java.util.Map;

/**
 * Tag repository interface
 */
public interface TagRepository {
    /**
     * Find tag by id with tags and tags. If tag not found it's returning null
     *
     * @param id Id of tag
     * @return tag entity
     */
    Tag findById(long id);

    /**
     * Delete tag by id
     *
     * @param tag Tag to delete
     */
    void delete(Tag tag);

    /**
     * Save tag entity with all dependencies and insert generated keys
     *
     * @param tag tag entity to saveBatch
     */
    void create(Tag tag);

    /**
     * Save tag entity with all dependencies
     *
     * @param tag tag entity to saveBatch
     * @return Tag entity with generated keys
     */
    Tag update(Tag tag);

    /**
     * Find all tags with number of news for each. Sorted by news count
     *
     * @return Map which contains tag as a key and count of news as a value objects
     */
    Map<Tag, Long> findAllWithNewsCount();
}
