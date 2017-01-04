package by.bsu.diplom.newshub.repository;

import by.bsu.diplom.newshub.domain.dto.SearchCriteria;
import by.bsu.diplom.newshub.domain.entity.News;

import java.util.List;

/**
 * News repository interface
 */
public interface NewsRepository {

    /**
     * Find news by id with authors and tags. If news not found it's returning null
     *
     * @param id Id of news
     * @return news entity
     */
    News findById(long id);

    /**
     * Delete news by id
     *
     * @param news News to delete
     */
    void delete(News news);

    /**
     * Save news entity with all dependencies
     *
     * @param news news entity to saveBatch
     * @return News entity with generated keys
     */
    News create(News news);

    /**
     * Save news entity with all dependencies
     *
     * @param news news entity to saveBatch
     * @return News entity with generated keys
     */
    News update(News news);

    /**
     * Find part of news in specified range sorted by some field
     *
     * @param offset       Number of news to skip
     * @param limit        Number of news to extract
     * @param sortingField Name of field to sort results
     * @return List of news
     */
    List<News> findInRange(int offset, int limit, String sortingField);

    /**
     * Find part of news by list of tags and authors sorted by some field
     *
     * @param searchCriteria entity with list of tags and authors
     * @param offset         Number of news to skip
     * @param limit          Number of news to extract
     * @param sortingField   Name of field to sort results
     * @return List of news
     */
    List<News> findInRange(SearchCriteria searchCriteria, int offset, int limit, String sortingField);

    /**
     * Count all news
     *
     * @return Number of news
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

