package by.bsu.diplom.newshub.domain.dto;


import by.bsu.diplom.newshub.domain.dto.error.ValidationErrorMessages;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Tag with count of his news
 */
public class TagDto {
    private Long id;

    @NotNull(message = ValidationErrorMessages.INVALID_NAME)
    @Pattern(regexp = "[A-Za-zА-Яа-я0-9\\-_.]{2,20}", message = ValidationErrorMessages.INVALID_NAME)
    private String name;

    private Long newsCount;

    public TagDto() {
    }

    public TagDto(Long id, String name, Long newsCount) {
        this.id = id;
        this.name = name;
        this.newsCount = newsCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

        TagDto tagDto = (TagDto) o;

        return (id != null ? id.equals(tagDto.id) : tagDto.id == null) && (name != null ? name.equals(tagDto.name) : tagDto.name == null);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
