package by.bsu.diplom.newshub.web.controller;


import by.bsu.diplom.newshub.domain.dto.TagDto;
import by.bsu.diplom.newshub.service.TagService;
import by.bsu.diplom.newshub.web.annotation.JsonRestController;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@JsonRestController("/tags")
public class TagController {
    private TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(value = "/{id}")
    public TagDto getTag(@PathVariable long id) {
        return tagService.findById(id);
    }

    @GetMapping
    public List<TagDto> getTags() {
        return tagService.findAllWithNewsCount();
    }

    @PostMapping
    public TagDto createTag(@Valid @RequestBody TagDto tag) {
        return tagService.create(tag);
    }

    @PutMapping(value = "/{id}")
    public TagDto updateTag(@PathVariable long id, @Valid @RequestBody TagDto tag) {
        tag.setId(id);
        return tagService.update(tag);
    }
}
