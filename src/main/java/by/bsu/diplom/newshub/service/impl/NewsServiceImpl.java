package by.bsu.diplom.newshub.service.impl;

import by.bsu.diplom.newshub.domain.dto.AuthorDto;
import by.bsu.diplom.newshub.domain.dto.NewsDto;
import by.bsu.diplom.newshub.domain.dto.SearchCriteria;
import by.bsu.diplom.newshub.domain.dto.TagDto;
import by.bsu.diplom.newshub.domain.entity.Author;
import by.bsu.diplom.newshub.domain.entity.News;
import by.bsu.diplom.newshub.domain.entity.Tag;
import by.bsu.diplom.newshub.exception.BatchServiceException;
import by.bsu.diplom.newshub.exception.EntityNotFoundException;
import by.bsu.diplom.newshub.repository.CommentRepository;
import by.bsu.diplom.newshub.repository.NewsRepository;
import by.bsu.diplom.newshub.service.BatchService;
import by.bsu.diplom.newshub.service.NewsService;
import by.bsu.diplom.newshub.service.validation.Validation;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service for operations with news entity
 * Also implements {@link BatchService}, so can save batches of news
 */
@Service
@Transactional
public class NewsServiceImpl implements NewsService, BatchService<NewsDto> {
    private static final String CREATION_DATE_FIELD = "creationDate";

    private NewsRepository newsRepository;
    private CommentRepository commentRepository;

    public NewsServiceImpl(NewsRepository newsRepository, CommentRepository commentRepository) {
        this.newsRepository = newsRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public NewsDto findById(long id) {
        News news = newsRepository.findById(id);
        if (news == null) {
            throw new EntityNotFoundException("News not found");
        }
        return convertToDto(news);
    }

    @Override
    public void delete(long id) {
        News news = newsRepository.findById(id);
        if (news == null) {
            throw new EntityNotFoundException("News not found");
        }
        news.getAuthors().clear();
        news.getTags().clear();
        newsRepository.delete(news);
    }

    @Override
    public NewsDto create(NewsDto newsDto) {
        News news = convertToEntity(newsDto);
        news.setId(null);
        news.setCreationDate(LocalDateTime.now());
        return convertToDto(newsRepository.create(news));
    }

    @Override
    @Validation
    public void saveBatch(@Validated List<NewsDto> newsDtos) throws BatchServiceException {
        try {
            List<News> newsList = convertToNewsList(newsDtos);
            newsList.forEach(news -> {
                news.setCreationDate(LocalDateTime.now());
                newsRepository.create(news);
            });
        } catch (DataAccessException ex) {
            throw new BatchServiceException("Error during batch saving", ex);
        }
    }

    @Override
    public NewsDto update(NewsDto newsDto) {
        News news = convertToEntity(newsDto);
        news.setCreationDate(findById(news.getId()).getCreationDate());
        news.setModificationDate(LocalDateTime.now());
        return convertToDto(newsRepository.update(news));
    }

    @Override
    public List<NewsDto> findPage(int pageNumber, int pageSize) {
        return convertToDtoList(newsRepository.findInRange((pageNumber - 1) * pageSize, pageSize, CREATION_DATE_FIELD));
    }

    @Override
    public List<NewsDto> findPage(SearchCriteria searchCriteria, int pageNumber, int pageSize) {
        return convertToDtoList(newsRepository.findInRange(searchCriteria, (pageNumber - 1) * pageSize, pageSize, CREATION_DATE_FIELD));
    }

    @Override
    public long count() {
        return newsRepository.count();
    }

    @Override
    public long count(SearchCriteria searchCriteria) {
        return newsRepository.count(searchCriteria);
    }

    private List<NewsDto> convertToDtoList(List<News> news) {
        List<NewsDto> newsDtos = new ArrayList<>();
        news.forEach(newsEntity -> newsDtos.add(new NewsDto(newsEntity.getId(),
                newsEntity.getTitle(),
                newsEntity.getBrief(),
                commentRepository.count(newsEntity),
                newsEntity.getCreationDate(),
                newsEntity.getModificationDate())));
        return newsDtos;
    }

    private List<News> convertToNewsList(List<NewsDto> newsDtoList) {
        List<News> news = new ArrayList<>();
        newsDtoList.forEach(newsDTO -> news.add(convertToEntity(newsDTO)));
        return news;
    }

    private NewsDto convertToDto(News news) {
        NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        newsDto.setTitle(news.getTitle());
        newsDto.setBrief(news.getBrief());
        newsDto.setContent(news.getContent());
        newsDto.setCommentsCount(commentRepository.count(news));
        newsDto.setAuthors(convertToAuthorDto(news.getAuthors()));
        newsDto.setTags(convertToTagDto(news.getTags()));
        newsDto.setCreationDate(news.getCreationDate());
        newsDto.setModificationDate(news.getModificationDate());
        return newsDto;
    }

    private News convertToEntity(NewsDto newsDto) {
        News news = new News();
        news.setId(newsDto.getId());
        news.setTitle(newsDto.getTitle());
        news.setBrief(newsDto.getBrief());
        news.setContent(newsDto.getContent());
        news.setAuthors(convertToAuthorEntity(newsDto.getAuthors()));
        news.setTags(convertToTagEntity(newsDto.getTags()));
        return news;
    }

    private Set<TagDto> convertToTagDto(Set<Tag> tags) {
        Set<TagDto> tagDtos = new HashSet<>();
        if (tags != null) {
            tags.forEach((tag) -> tagDtos.add(new TagDto(tag.getId(), tag.getName(), 0L)));
        }
        return tagDtos;
    }

    private Set<Tag> convertToTagEntity(Set<TagDto> tagDtos) {
        Set<Tag> tags = new HashSet<>();
        if (tagDtos != null) {
            tagDtos.forEach((tagDto) -> {
                Tag tag = new Tag();
                tag.setId(tagDto.getId());
                tag.setName(tag.getName());
                tags.add(tag);
            });
        }
        return tags;
    }

    private Set<Author> convertToAuthorEntity(Set<AuthorDto> authorDtos) {
        Set<Author> authors = new HashSet<>();
        if (authorDtos != null) {
            authorDtos.forEach((authorDTO) -> {
                Author author = new Author();
                author.setId(authorDTO.getId());
                author.setFullName(authorDTO.getFullName());
                authors.add(author);
            });
        }
        return authors;
    }

    private Set<AuthorDto> convertToAuthorDto(Set<Author> authors) {
        Set<AuthorDto> authorDtos = new HashSet<>();
        if (authors != null) {
            authors.forEach((author) -> authorDtos.add(new AuthorDto(author.getId(), author.getFullName(), 0L)));
        }
        return authorDtos;
    }
}
