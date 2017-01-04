package by.bsu.diplom.newshub.service;

import by.bsu.diplom.newshub.domain.dto.TagDto;
import by.bsu.diplom.newshub.exception.EntityNotFoundException;

import java.util.List;

/**
 * Tag  service interface
 */
public interface TagService {
    /**
     * Find entity by id
     *
     * @param id pk of news
     * @return Found tag
     * @throws EntityNotFoundException If entity with such id not found
     */
    TagDto findById(long id) throws EntityNotFoundException;


    /**
     * Find all tags with number of news for each. Sorted by news count
     *
     * @return List which contains TagDto objects
     */
    List<TagDto> findAllWithNewsCount();

    /**
     * Delete entity by id
     *
     * @param id pk of entity
     * @throws EntityNotFoundException If entity with such id not found
     */
    void delete(long id) throws EntityNotFoundException;

    /**
     * Create entity
     *
     * @param tagDto Created entity dto
     * @return Entity dto with generated keys
     */
    TagDto create(TagDto tagDto);

    /**
     * Update entity
     *
     * @param tagDto Updated entity dto
     * @return Entity dto
     */
    TagDto update(TagDto tagDto);
}
