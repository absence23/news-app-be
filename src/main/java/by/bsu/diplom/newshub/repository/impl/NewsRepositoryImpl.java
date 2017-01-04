package by.bsu.diplom.newshub.repository.impl;

import by.bsu.diplom.newshub.domain.dto.SearchCriteria;
import by.bsu.diplom.newshub.domain.entity.Author;
import by.bsu.diplom.newshub.domain.entity.News;
import by.bsu.diplom.newshub.domain.entity.Tag;
import by.bsu.diplom.newshub.repository.NewsRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import java.util.List;
import java.util.Set;

@Repository
public class NewsRepositoryImpl implements NewsRepository {
    private static final String ID = "id";
    private static final String NEWS = "news";

    @PersistenceContext
    private EntityManager entityManager;

    public News findById(long id) {
        return entityManager.find(News.class, id);
    }

    @Override
    public void delete(News news) {
        entityManager.remove(news);
    }

    @Override
    public News create(News news) {
        return entityManager.merge(news);
    }

    @Override
    public News update(News news) {
        return entityManager.merge(news);
    }

    @Override
    public List<News> findInRange(int offset, int limit, String sortingField) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<News> query = criteriaBuilder.createQuery(News.class);
        Root<News> newsRoot = query.from(News.class);
        query.select(newsRoot);
        query.orderBy(criteriaBuilder.desc(newsRoot.get(sortingField)));
        return entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public long count() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        query.select(criteriaBuilder.count(query.from(News.class)));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public List<News> findInRange(SearchCriteria searchCriteria, int offset, int limit, String sortingField) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<News> query = criteriaBuilder.createQuery(News.class);
        Root<News> newsRoot = query.from(News.class);
        formQuery(searchCriteria, query, newsRoot, criteriaBuilder);
        query.orderBy(criteriaBuilder.desc(newsRoot.get(sortingField)));
        return entityManager.createQuery(query)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public long count(SearchCriteria searchCriteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<News> newsRoot = query.from(News.class);
        formQuery(searchCriteria, query, newsRoot, criteriaBuilder);
        return entityManager.createQuery(query.select(criteriaBuilder.count(newsRoot.get(ID)))).getSingleResult();
    }

    private Subquery<Long> formTagQuery(Set<Long> ids, AbstractQuery<?> query, CriteriaBuilder criteriaBuilder, EntityType<News> newsEntityType) {
        Subquery<Long> newsTags = null;
        if (!ids.isEmpty()) {
            newsTags = query.subquery(Long.class);
            Root<Tag> tagRoot = newsTags.from(Tag.class);
            Join<Tag, News> tagNewsJoin = tagRoot.join(NEWS);
            newsTags.select(tagNewsJoin.get(newsEntityType.getId(Long.class)))
                    .where(tagRoot.get(ID).in(ids))
                    .groupBy(tagNewsJoin.get(newsEntityType.getId(Long.class)))
                    .having(criteriaBuilder.equal(criteriaBuilder.count(tagNewsJoin.get(newsEntityType.getId(Long.class))),
                            ids.size()));
        }
        return newsTags;
    }

    private Subquery<Long> formAuthorsQuery(Set<Long> ids, AbstractQuery<?> query, CriteriaBuilder criteriaBuilder, EntityType<News> newsEntityType) {
        Subquery<Long> newsAuthors = null;
        if (!ids.isEmpty()) {
            newsAuthors = query.subquery(Long.class);
            Root<Author> authorRoot = newsAuthors.from(Author.class);
            Join<Author, News> authorNewsJoin = authorRoot.join(NEWS);
            newsAuthors.select(authorNewsJoin.get(newsEntityType.getId(Long.class)))
                    .where(authorRoot.get(ID).in(ids))
                    .groupBy(authorNewsJoin.get(newsEntityType.getId(Long.class)))
                    .having(criteriaBuilder.equal(criteriaBuilder.count(authorNewsJoin.get(newsEntityType.getId(Long.class))),
                            ids.size()));
        }
        return newsAuthors;
    }

    private void formQuery(SearchCriteria searchCriteria, AbstractQuery<?> query, Root<News> newsRoot, CriteriaBuilder criteriaBuilder) {
        EntityType<News> newsEntityType = entityManager.getMetamodel().entity(News.class);
        Subquery<Long> newsTags = formTagQuery(searchCriteria.getTags(), query, criteriaBuilder, newsEntityType);
        Subquery<Long> newsAuthors = formAuthorsQuery(searchCriteria.getAuthors(), query, criteriaBuilder, newsEntityType);
        if (newsTags != null && newsAuthors != null) {
            query.where(criteriaBuilder.and(newsRoot.get(ID).in(newsTags)), newsRoot.get(ID).in(newsAuthors));
        } else if (newsTags != null) {
            query.where(newsRoot.get(ID).in(newsTags));
        } else {
            query.where(newsRoot.get(ID).in(newsAuthors));
        }
    }
}
