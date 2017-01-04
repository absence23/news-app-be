package by.bsu.diplom.newshub.domain.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
public class News implements Serializable {
    @Id
    @SequenceGenerator(name = "news_id_seq", sequenceName = "news_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "news_id_seq")
    private Long id;
    private String title;
    private String brief;
    private String content;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;

    @OneToMany(mappedBy = "news", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(name = "news_tags",
            joinColumns = {@JoinColumn(name = "news_id", nullable = false)},
            inverseJoinColumns = @JoinColumn(name = "tag_id", nullable = false))
    private Set<Tag> tags;

    @ManyToMany
    @JoinTable(name = "news_authors",
            joinColumns = @JoinColumn(name = "news_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "author_id", nullable = false))
    private Set<Author> authors;

    public News() {
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        News news = (News) o;

        return (title != null ? title.equals(news.title) : news.title == null) && (brief != null ? brief.equals(news.brief) : news.brief == null);
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (brief != null ? brief.hashCode() : 0);
        return result;
    }
}
