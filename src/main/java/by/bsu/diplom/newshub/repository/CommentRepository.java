package by.bsu.diplom.newshub.repository;

import by.bsu.diplom.newshub.domain.entity.Comment;
import by.bsu.diplom.newshub.domain.entity.News;

import java.util.List;

/**
 * Comments repository interface
 */
public interface CommentRepository {
    /**
     * Find comment by id with authors and tags. If comment not found it's returning null
     *
     * @param id Id of comment
     * @return comment entity
     */
    Comment findById(long id);

    /**
     * Delete comment by id
     *
     * @param comment Comment to delete
     */
    void delete(Comment comment);

    /**
     * Save comment entity with all dependencies
     *
     * @param comment comment entity to saveBatch
     * @return Comment entity with generated keys
     */
    Comment create(Comment comment);

    /**
     * Save comment entity with all dependencies
     *
     * @param comment comment entity to saveBatch
     * @return Comment entity with generated keys
     */
    Comment update(Comment comment);

    /**
     * Find piece of comments for some news in specified range sorted by some field
     *
     * @param news         News for which comments wil be found
     * @param offset       Number of comment to skip
     * @param limit        Number of comment to extract
     * @param sortingField Name of field to sort results
     * @return List of comment
     */
    List<Comment> findInRange(News news, int offset, int limit, String sortingField);

    /**
     * Count all comments for some news
     *
     * @param news News for which comments will be counted
     * @return Number of comment
     */
    long count(News news);
}
