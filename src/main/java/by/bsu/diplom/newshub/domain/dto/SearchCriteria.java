package by.bsu.diplom.newshub.domain.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * Dto object for transferring set of tags' and authors ids
 * that will be using for filtering
 */
public class SearchCriteria {
    private Set<Long> tags = new HashSet<>();
    private Set<Long> authors = new HashSet<>();

    public SearchCriteria() {
    }

    public SearchCriteria(Set<Long> tags, Set<Long> authors) {
        this.tags = tags;
        this.authors = authors;
    }

    public Set<Long> getTags() {
        return tags;
    }

    public void setTags(Set<Long> tags) {
        this.tags = tags;
    }

    public Set<Long> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Long> authors) {
        this.authors = authors;
    }
}
