package by.bsu.diplom.newshub.web.controller;

import by.bsu.diplom.newshub.domain.dto.CommentDto;
import by.bsu.diplom.newshub.service.CommentService;
import by.bsu.diplom.newshub.web.annotation.JsonRestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@JsonRestController("/news/{newsId}/comments")
public class CommentController {
    private final static String DEFAULT_PAGE = "1";
    private final static String DEFAULT_PAGE_SIZE = "10";
    private static final String PAGINATION_COUNT_HEADER_NAME = "X-Pagination-Count";

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping(value = "/{id}")
    public CommentDto getComment(@PathVariable long id) {
        return commentService.findById(id);
    }

    @GetMapping
    public List<CommentDto> getCommentPage(@PathVariable long newsId,
                                           @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                           @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size,
                                           HttpServletResponse response) {
        response.addHeader(PAGINATION_COUNT_HEADER_NAME, String.valueOf(commentService.count(newsId)));
        return commentService.findPage(newsId, page, size);
    }


    @PostMapping
    public CommentDto createComment(@PathVariable long newsId, @Valid @RequestBody CommentDto commentDto) {
        commentDto.setNewsId(newsId);
        return commentService.create(commentDto);
    }

    @PutMapping(value = "/{id}")
    public CommentDto updateComment(@PathVariable long newsId, @PathVariable long id, @Valid @RequestBody CommentDto commentDto) {
        commentDto.setId(id);
        commentDto.setNewsId(newsId);
        return commentService.update(commentDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable long id) {
        commentService.delete(id);
    }
}
