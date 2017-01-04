package by.bsu.diplom.newshub.service;

import by.bsu.diplom.newshub.domain.dto.NewsDto;
import by.bsu.diplom.newshub.domain.entity.News;
import by.bsu.diplom.newshub.exception.EntityNotFoundException;
import by.bsu.diplom.newshub.repository.CommentRepository;
import by.bsu.diplom.newshub.repository.NewsRepository;
import by.bsu.diplom.newshub.service.impl.NewsServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;

import java.time.LocalDateTime;
import java.util.HashSet;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = TestContext.class)
public class NewsServiceTest {
    @Mock
    NewsRepository newsRepository;
    @Mock
    CommentRepository commentRepository;

    @InjectMocks
    NewsServiceImpl newsService;

    @Test
    public void findTest() {
        News news = new News();
        NewsDto newsDto;
        news.setId(1L);
        news.setTitle("title");
        news.setBrief("brief");
        news.setContent("content");
        news.setCreationDate(LocalDateTime.now());
        news.setAuthors(new HashSet<>());
        news.setTags(new HashSet<>());
        newsDto = new NewsDto(news.getId(), news.getTitle(), news.getBrief(), news.getContent(), 1L, null, null, news.getCreationDate(), news.getModificationDate());
        Mockito.when(commentRepository.count(Mockito.any())).thenReturn(1L);
        Mockito.when(newsRepository.findById(0)).thenReturn(null);
        Mockito.when(newsRepository.findById(1)).thenReturn(news);
        Assert.assertTrue(newsService.findById(1L).equals(newsDto));
        try {
            newsService.findById(0L);
            Assert.fail();
        } catch (EntityNotFoundException ignored) {
        }
    }

    @Test
    public void deleteTest() {
        News news = new News();
        news.setId(1L);
        news.setTitle("title");
        news.setBrief("brief");
        news.setContent("content");
        news.setCreationDate(LocalDateTime.now());
        news.setAuthors(new HashSet<>());
        news.setTags(new HashSet<>());
        Mockito.when(commentRepository.count(Mockito.any())).thenReturn(1L);
        Mockito.when(newsRepository.findById(0)).thenReturn(null);
        Mockito.when(newsRepository.findById(1)).thenReturn(news);
        Mockito.doNothing().when(newsRepository).delete(Mockito.any());
        newsService.delete(1L);
        try {
            newsService.delete(0L);
            Assert.fail();
        } catch (EntityNotFoundException ignored) {
        }
    }

    @Test
    public void createTest() {
        News news = new News();
        NewsDto newsDto;
        news.setId(1L);
        news.setTitle("title");
        news.setBrief("brief");
        news.setContent("content");
        news.setCreationDate(LocalDateTime.now());
        news.setAuthors(new HashSet<>());
        news.setTags(new HashSet<>());
        newsDto = new NewsDto(news.getId(), news.getTitle(), news.getBrief(), news.getContent(), 1L, null, null, news.getCreationDate(), news.getModificationDate());
        Mockito.when(commentRepository.count(Mockito.any())).thenReturn(1L);
        Mockito.when(newsRepository.findById(0)).thenReturn(null);
        Mockito.when(newsRepository.findById(1)).thenReturn(news);
        Mockito.when(newsRepository.create(news)).then(invocation -> {
            news.setId(1L);
            return news;
        });
        Assert.assertTrue(newsService.create(newsDto).equals(newsDto));
    }

}
