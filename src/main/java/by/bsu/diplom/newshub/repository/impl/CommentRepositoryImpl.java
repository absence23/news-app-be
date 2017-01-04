package by.bsu.diplom.newshub.repository.impl;

import by.bsu.diplom.newshub.domain.entity.Comment;
import by.bsu.diplom.newshub.domain.entity.News;
import by.bsu.diplom.newshub.repository.CommentRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CommentRepositoryImpl implements CommentRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Comment findById(long id) {
        return entityManager.find(Comment.class, id);
    }

    @Override
    public void delete(Comment comment) {
        comment = entityManager.merge(comment);
        entityManager.remove(comment);
    }

    @Override
    public Comment create(Comment comment) {
        entityManager.persist(comment);
        entityManager.flush();
        return comment;
    }

    @Override
    public Comment update(Comment comment) {
        return entityManager.merge(comment);
    }

    @Override
    public List<Comment> findInRange(News news, int offset, int limit, String sortingField) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> query = criteriaBuilder.createQuery(Comment.class);
        Root<Comment> commentRoot = query.from(Comment.class);
        query.select(commentRoot);
        query.where(criteriaBuilder.equal(commentRoot.get("news"), news));
        query.orderBy(criteriaBuilder.desc(commentRoot.get(sortingField)));
        return entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public long count(News news) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Comment> commentRoot = query.from(Comment.class);
        query.select(criteriaBuilder.count(commentRoot));
        query.where(criteriaBuilder.equal(commentRoot.get("news"), news));
        return entityManager.createQuery(query).getSingleResult();
    }
}
