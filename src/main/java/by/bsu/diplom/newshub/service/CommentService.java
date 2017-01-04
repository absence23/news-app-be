package by.bsu.diplom.newshub.service;

import by.bsu.diplom.newshub.domain.dto.CommentDto;
import by.bsu.diplom.newshub.exception.EntityNotFoundException;

import java.util.List;

/**
 * Comment service interface
 */
public interface CommentService {

    /**
     * Find entity by id
     *
     * @param id pk of news
     * @return Found comment
     * @throws EntityNotFoundException If entity with such id not found
     */
    CommentDto findById(long id) throws EntityNotFoundException;

    /**
     * Delete entity by id
     *
     * @param id pk of entity
     */
    void delete(long id);

    /**
     * Create entity
     *
     * @param comment Created entity dto
     * @return Entity with generated keys
     */
    CommentDto create(CommentDto comment);

    /**
     * Update entity
     *
     * @param comment Updated entity dto
     * @return Entity dto
     */
    CommentDto update(CommentDto comment);


    /**
     * @param newsId     Id of news for which comments will be found
     * @param pageNumber Number of page to skip
     * @param pageSize   Limit of news to find
     * @return List of comments for news
     */
    List<CommentDto> findPage(long newsId, int pageNumber, int pageSize);

    /**
     * @param newsId Id of news for which comments will be counted
     * @return Total number of comments for news
     */
    long count(long newsId);
}
