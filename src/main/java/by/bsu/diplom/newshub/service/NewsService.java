package by.bsu.diplom.newshub.service;


import by.bsu.diplom.newshub.domain.dto.NewsDto;
import by.bsu.diplom.newshub.domain.dto.SearchCriteria;
import by.bsu.diplom.newshub.exception.EntityNotFoundException;

import java.util.List;

/**
 * News service interface
 */
public interface NewsService {

    /**
     * Find entity by id
     *
     * @param id pk of news
     * @return Found newsDTO
     * @throws EntityNotFoundException If entity with such id not found
     */
    NewsDto findById(long id) throws EntityNotFoundException;

    /**
     * Delete entity by id
     *
     * @param id pk of entity
     */
    void delete(long id);

    /**
     * Create entity
     *
     * @param newsDto Created entity
     * @return Entity with generated keys
     */
    NewsDto create(NewsDto newsDto);

    /**
     * Update entity
     *
     * @param newsDto Updated entity
     */
    NewsDto update(NewsDto newsDto);


    /**
     * @param pageNumber Number of pages to skip
     * @param pageSize   Limit of news to find
     * @return List of newsDTO
     */
    List<NewsDto> findPage(int pageNumber, int pageSize);

    /**
     * Find news filtered by authors and tags
     *
     * @param searchCriteria Entity with list of tags and authors
     * @param pageNumber     Number of pages to skip
     * @param pageSize       Limit of news to find
     * @return Filtered list of newsDTO
     */
    List<NewsDto> findPage(SearchCriteria searchCriteria, int pageNumber, int pageSize);

    /**
     * @return Total number of news
     */
    long count();

    /**
     * Count news that suits to searchCriteria object
     *
     * @param searchCriteria entity with list of tags and authors
     * @return Count of suitable news
     */
    long count(SearchCriteria searchCriteria);
}
