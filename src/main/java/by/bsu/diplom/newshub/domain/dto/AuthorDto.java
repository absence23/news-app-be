package by.bsu.diplom.newshub.domain.dto;

import by.bsu.diplom.newshub.domain.dto.error.ValidationErrorMessages;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Author with count of his news
 */
public class AuthorDto {
    private Long id;

    @NotNull(message = ValidationErrorMessages.INVALID_NAME)
    @Pattern(regexp = "[A-Za-zА-Яа-я\\s\\-]{5,50}", message = ValidationErrorMessages.INVALID_NAME)
    private String fullName;

    private Long newsCount;

    public AuthorDto() {
    }

    public AuthorDto(Long id, String fullName, Long newsCount) {
        this.id = id;
        this.fullName = fullName;
        this.newsCount = newsCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getNewsCount() {
        return newsCount;
    }

    public void setNewsCount(Long newsCount) {
        this.newsCount = newsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AuthorDto authorDto = (AuthorDto) o;

        return (id != null ? id.equals(authorDto.id) : authorDto.id == null) && (fullName != null ? fullName.equals(authorDto.fullName) : authorDto.fullName == null);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        return result;
    }
}
