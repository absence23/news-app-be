package by.bsu.diplom.newshub.repository;

import by.bsu.diplom.newshub.domain.entity.Author;

import java.util.Map;

/**
 * Author repository interface
 */
public interface AuthorRepository {
    /**
     * Find author by id with authors and tags. If author not found it's returning null
     *
     * @param id Id of author
     * @return author entity
     */
    Author findById(long id);

    /**
     * Delete author by id
     *
     * @param author Author to delete
     */
    void delete(Author author);

    /**
     * Save author entity with all dependencies and insert generated keys
     *
     * @param author author entity to saveBatch
     */
    void create(Author author);

    /**
     * Save author entity with all dependencies
     *
     * @param author author entity to saveBatch
     * @return Author entity with generated keys
     */
    Author update(Author author);

    /**
     * Find all authors with number of news for each. Sorted by news count
     *
     * @return Map which contains author as a key and count of news as a value objects
     */
    Map<Author, Long> findAllWithNewsCount();
}
