package by.bsu.diplom.newshub.service.impl;

import by.bsu.diplom.newshub.domain.dto.TagDto;
import by.bsu.diplom.newshub.domain.entity.Tag;
import by.bsu.diplom.newshub.exception.EntityAlreadyExistsException;
import by.bsu.diplom.newshub.exception.EntityNotFoundException;
import by.bsu.diplom.newshub.repository.TagRepository;
import by.bsu.diplom.newshub.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public TagDto findById(long id) throws EntityNotFoundException {
        Tag tag = tagRepository.findById(id);
        if (tag == null) {
            throw new EntityNotFoundException("Tag not found");
        }
        return convertToDTO(tag);
    }

    @Override
    public List<TagDto> findAllWithNewsCount() {
        Map<Tag, Long> tags = tagRepository.findAllWithNewsCount();
        List<TagDto> tagDtos = new ArrayList<>();
        tags.forEach((tag, count) -> {
            TagDto tagDto = convertToDTO(tag);
            tagDto.setNewsCount(count);
            tagDtos.add(tagDto);
        });
        tagDtos.sort((o1, o2) -> o2.getNewsCount().compareTo(o1.getNewsCount()));
        return tagDtos;
    }

    @Override
    public void delete(long id) throws EntityNotFoundException {
        tagRepository.delete(convertToEntity(findById(id)));
    }

    @Override
    public TagDto create(TagDto tagDto) {
        try {
            Tag tag = convertToEntity(tagDto);
            tag.setId(null);
            tagRepository.create(tag);
            return convertToDTO(tag);
        } catch (PersistenceException ex) {
            throw new EntityAlreadyExistsException("Tag with such name already exists", ex);
        }
    }

    @Override
    public TagDto update(TagDto tagDto) {
        try {
            findById(tagDto.getId());
            return convertToDTO(tagRepository.update(convertToEntity(tagDto)));
        } catch (PersistenceException ex) {
            throw new EntityAlreadyExistsException("Tag with such name already exists", ex);
        }
    }


    private TagDto convertToDTO(Tag tag) {
        return new TagDto(tag.getId(), tag.getName(), 0L);
    }

    private Tag convertToEntity(TagDto tagDto) {
        Tag tag = new Tag();
        tag.setId(tagDto.getId());
        tag.setName(tagDto.getName());
        return tag;
    }
}
