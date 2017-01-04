package by.bsu.diplom.newshub.web.controller;

import by.bsu.diplom.newshub.domain.dto.NewsDto;
import by.bsu.diplom.newshub.domain.dto.SearchCriteria;
import by.bsu.diplom.newshub.service.NewsService;
import by.bsu.diplom.newshub.web.annotation.JsonRestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@CrossOrigin
@JsonRestController("/news")
public class NewsController {
    private final static String DEFAULT_PAGE = "1";
    private final static String DEFAULT_PAGE_SIZE = "10";
    private final static String DELIMITER_REGEX = "[,]";
    private static final String PAGINATION_COUNT_HEADER_NAME = "X-Pagination-Count";

    private NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping(value = "/{id}")
    public NewsDto getNews(@PathVariable long id) {
        return newsService.findById(id);
    }

    @GetMapping
    public List<NewsDto> getNewsPage(@RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                     @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size,
                                     @RequestParam(required = false) String authors,
                                     @RequestParam(required = false) String tags,
                                     HttpServletResponse response) {
        if ((authors != null && !authors.isEmpty()) || (tags != null && !tags.isEmpty())) {
            SearchCriteria searchCriteria = new SearchCriteria(parseIds(tags), parseIds(authors));
            response.addHeader(PAGINATION_COUNT_HEADER_NAME, String.valueOf(newsService.count(searchCriteria)));
            return newsService.findPage(searchCriteria, page, size);
        }
        response.addHeader(PAGINATION_COUNT_HEADER_NAME, String.valueOf(newsService.count()));
        return newsService.findPage(page, size);
    }

    @PostMapping
    public NewsDto createNews(@Valid @RequestBody NewsDto newsDto) {
        return newsService.create(newsDto);
    }

    @PutMapping(value = "/{id}")
    public NewsDto updateNews(@PathVariable long id, @Valid @RequestBody NewsDto newsDto) {
        newsDto.setId(id);
        return newsService.update(newsDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNews(@PathVariable long id) {
        newsService.delete(id);
    }

    private Set<Long> parseIds(String ids) {
        Set<Long> parsedIds = new HashSet<>();
        if (ids != null) {
            for (String id : ids.split(DELIMITER_REGEX)) {
                parsedIds.add(Long.valueOf(id));
            }
        }
        return parsedIds;
    }
}
