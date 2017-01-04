package by.bsu.diplom.newshub.domain.dto;

import by.bsu.diplom.newshub.domain.dto.error.ValidationErrorMessages;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Transfer object for comment entity
 */
public class CommentDto {
    private Long id;

    @NotNull(message = ValidationErrorMessages.EMPTY_CONTENT)
    @Size.List(value = {
            @Size(min = 1, message = ValidationErrorMessages.EMPTY_CONTENT),
            @Size(max = 500, message = ValidationErrorMessages.TOO_BIG_COMMENT_CONTENT)})
    private String text;

    private LocalDateTime creationDate;
    private Long newsId;

    public CommentDto() {
    }

    public CommentDto(Long id, String text, LocalDateTime creationDate, Long newsId) {
        this.id = id;
        this.text = text;
        this.creationDate = creationDate;
        this.newsId = newsId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommentDto that = (CommentDto) o;

        return (id != null ? id.equals(that.id) : that.id == null) && (text != null ? text.equals(that.text) : that.text == null);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }
}
