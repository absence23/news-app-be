package by.bsu.diplom.newshub.service.impl;

import by.bsu.diplom.newshub.domain.dto.AuthorDto;
import by.bsu.diplom.newshub.domain.entity.Author;
import by.bsu.diplom.newshub.exception.EntityAlreadyExistsException;
import by.bsu.diplom.newshub.exception.EntityNotFoundException;
import by.bsu.diplom.newshub.repository.AuthorRepository;
import by.bsu.diplom.newshub.service.AuthorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorDto findById(long id) throws EntityNotFoundException {
        Author author = authorRepository.findById(id);
        if (author == null) {
            throw new EntityNotFoundException("Author not found");
        }
        return convertToDTO(author);
    }

    @Override
    public List<AuthorDto> findAllWithNewsCount() {
        Map<Author, Long> authors = authorRepository.findAllWithNewsCount();
        List<AuthorDto> authorDtos = new ArrayList<>();
        authors.forEach((author, count) -> {
            AuthorDto authorDto = convertToDTO(author);
            authorDto.setNewsCount(count);
            authorDtos.add(authorDto);
        });
        authorDtos.sort((o1, o2) -> o2.getNewsCount().compareTo(o1.getNewsCount()));
        return authorDtos;
    }

    @Override
    public void delete(long id) {
        authorRepository.delete(convertToEntity(findById(id)));
    }

    @Override
    public AuthorDto create(AuthorDto authorDto) {
        try {
            Author author = convertToEntity(authorDto);
            author.setId(null);
            authorRepository.create(author);
            return convertToDTO(author);
        } catch (PersistenceException ex) {
            throw new EntityAlreadyExistsException("Author with such name already exists", ex);
        }
    }

    @Override
    public AuthorDto update(AuthorDto authorDto) {
        try {
            findById(authorDto.getId());
            return convertToDTO(authorRepository.update(convertToEntity(authorDto)));
        } catch (PersistenceException ex) {
            throw new EntityAlreadyExistsException("Author with such name already exists", ex);
        }
    }

    private AuthorDto convertToDTO(Author author) {
        return new AuthorDto(author.getId(), author.getFullName(), 0L);
    }

    private Author convertToEntity(AuthorDto authorDto) {
        Author author = new Author();
        author.setId(authorDto.getId());
        author.setFullName(authorDto.getFullName());
        return author;
    }
}