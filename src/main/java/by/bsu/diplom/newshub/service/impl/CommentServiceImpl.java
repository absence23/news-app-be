package by.bsu.diplom.newshub.service.impl;

import by.bsu.diplom.newshub.domain.dto.CommentDto;
import by.bsu.diplom.newshub.domain.entity.Comment;
import by.bsu.diplom.newshub.domain.entity.News;
import by.bsu.diplom.newshub.exception.EntityNotFoundException;
import by.bsu.diplom.newshub.repository.CommentRepository;
import by.bsu.diplom.newshub.repository.NewsRepository;
import by.bsu.diplom.newshub.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private static final String CREATION_DATE_FIELD = "creationDate";

    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;

    public CommentServiceImpl(CommentRepository commentRepository, NewsRepository newsRepository) {
        this.commentRepository = commentRepository;
        this.newsRepository = newsRepository;
    }

    @Override
    public CommentDto findById(long id) throws EntityNotFoundException {
        Comment comment = commentRepository.findById(id);
        if (comment == null) {
            throw new EntityNotFoundException("Comment not found");
        }
        return convertToDTO(comment);
    }

    @Override
    public void delete(long id) {
        commentRepository.delete(convertToEntity(findById(id)));
    }

    @Override
    public CommentDto create(CommentDto commentDto) {
        Comment comment = convertToEntity(commentDto);
        comment.setId(null);
        comment.setCreationDate(LocalDateTime.now());
        return convertToDTO(commentRepository.create(comment));
    }

    @Override
    public CommentDto update(CommentDto commentDto) {
        findById(commentDto.getId());
        Comment comment = convertToEntity(commentDto);
        return convertToDTO(commentRepository.update(comment));
    }

    @Override
    public List<CommentDto> findPage(long newsId, int pageNumber, int pageSize) {
        News news = newsRepository.findById(newsId);
        if (news == null) {
            throw new EntityNotFoundException("News entity not found");
        }
        return convertToDTOList(commentRepository.findInRange(news, (pageNumber - 1) * pageSize, pageSize, CREATION_DATE_FIELD));
    }

    @Override
    public long count(long newsId) {
        News news = newsRepository.findById(newsId);
        if (news == null) {
            throw new EntityNotFoundException("News entity not found");
        }
        return commentRepository.count(news);
    }

    private List<CommentDto> convertToDTOList(List<Comment> comments) {
        List<CommentDto> commentDtos = new ArrayList<>();
        comments.forEach(comment -> commentDtos.add(convertToDTO(comment)));
        return commentDtos;
    }

    private CommentDto convertToDTO(Comment comment) {
        return new CommentDto(comment.getId(),
                comment.getText(),
                comment.getCreationDate(),
                comment.getNews().getId());
    }

    private Comment convertToEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setText(commentDto.getText());
        comment.setCreationDate(commentDto.getCreationDate());
        comment.setNews(newsRepository.findById(commentDto.getNewsId()));
        if (comment.getNews() == null) {
            throw new EntityNotFoundException("News entity not found");
        }
        return comment;
    }
}
