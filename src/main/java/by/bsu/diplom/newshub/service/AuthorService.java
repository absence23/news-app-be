package by.bsu.diplom.newshub.service;

import by.bsu.diplom.newshub.domain.dto.AuthorDto;
import by.bsu.diplom.newshub.exception.EntityNotFoundException;

import java.util.List;


/**
 * Author service interface
 */
public interface AuthorService {

    /**
     * Find entity by id
     *
     * @param id pk of news
     * @return Found author
     * @throws EntityNotFoundException If entity with such id not found
     */
    AuthorDto findById(long id) throws EntityNotFoundException;


    /**
     * Find all authors with number of news for each. Sorted by news count
     *
     * @return List which contains AuthorDto objects
     */
    List<AuthorDto> findAllWithNewsCount();

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
     * @param authorDto Created entity dto
     * @return Entity dto with generated keys
     */
    AuthorDto create(AuthorDto authorDto);

    /**
     * Update entity
     *
     * @param authorDto Updated entity dto
     * @return Entity dto
     */
    AuthorDto update(AuthorDto authorDto);

}
