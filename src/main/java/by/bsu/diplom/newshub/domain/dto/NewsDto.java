package by.bsu.diplom.newshub.domain.dto;

import by.bsu.diplom.newshub.domain.dto.error.ValidationErrorMessages;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Transfer object for News entity
 */
public class NewsDto {
    private Long id;

    @NotNull(message = ValidationErrorMessages.EMPTY_TITLE)
    @Size.List(value = {
            @Size(min = 1, message = ValidationErrorMessages.EMPTY_TITLE),
            @Size(max = 200, message = ValidationErrorMessages.TOO_BIG_TITLE)})
    private String title;

    @NotNull(message = ValidationErrorMessages.EMPTY_BRIEF)
    @Size.List(value = {
            @Size(min = 1, message = ValidationErrorMessages.EMPTY_BRIEF),
            @Size(max = 500, message = ValidationErrorMessages.TOO_BIG_BRIEF)})
    private String brief;

    @NotNull(message = ValidationErrorMessages.EMPTY_CONTENT)
    @Size.List(value = {
            @Size(min = 1, message = ValidationErrorMessages.EMPTY_CONTENT),
            @Size(max = 20000, message = ValidationErrorMessages.TOO_BIG_CONTENT)})
    private String content;

    private long commentsCount;

    @NotNull(message = ValidationErrorMessages.EMPTY_AUTHORS)
    @Size(min = 1, message = ValidationErrorMessages.EMPTY_AUTHORS)
    private Set<AuthorDto> authors;

    private Set<TagDto> tags;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;

    public NewsDto() {
    }

    public NewsDto(Long id, String title, String brief, LocalDateTime creationDate, LocalDateTime modificationDate) {
        this.id = id;
        this.title = title;
        this.brief = brief;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }

    public NewsDto(Long id, String title, String brief, long commentsCount, LocalDateTime creationDate, LocalDateTime modificationDate) {
        this.id = id;
        this.title = title;
        this.brief = brief;
        this.commentsCount = commentsCount;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }

    public NewsDto(Long id, String title, String brief, String content, long commentsCount, Set<AuthorDto> authors, Set<TagDto> tags, LocalDateTime creationDate, LocalDateTime modificationDate) {
        this.id = id;
        this.title = title;
        this.brief = brief;
        this.content = content;
        this.commentsCount = commentsCount;
        this.authors = authors;
        this.tags = tags;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<AuthorDto> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorDto> authors) {
        this.authors = authors;
    }

    public Set<TagDto> getTags() {
        return tags;
    }

    public void setTags(Set<TagDto> tags) {
        this.tags = tags;
    }

    public long getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(long commentsCount) {
        this.commentsCount = commentsCount;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NewsDto newsDto = (NewsDto) o;

        return (id != null ? id.equals(newsDto.id) : newsDto.id == null) && (title != null ? title.equals(newsDto.title) : newsDto.title == null);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}
