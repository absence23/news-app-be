package by.bsu.diplom.newshub.web.controller;

import by.bsu.diplom.newshub.domain.dto.AuthorDto;
import by.bsu.diplom.newshub.service.AuthorService;
import by.bsu.diplom.newshub.web.annotation.JsonRestController;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@JsonRestController("/authors")
public class AuthorController {
    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(value = "/{id}")
    public AuthorDto getAuthor(@PathVariable long id) {
        return authorService.findById(id);
    }

    @GetMapping
    public List<AuthorDto> getAuthors() {
        return authorService.findAllWithNewsCount();
    }

    @PostMapping
    public AuthorDto createAuthor(@Valid @RequestBody AuthorDto author) {
        return authorService.create(author);
    }

    @PutMapping(value = "/{id}")
    public AuthorDto updateAuthor(@PathVariable long id, @Valid @RequestBody AuthorDto author) {
        author.setId(id);
        return authorService.update(author);
    }
}
