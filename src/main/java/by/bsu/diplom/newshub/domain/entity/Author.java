package by.bsu.diplom.newshub.domain.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@NamedQuery(name = "Author.findWithCount", query = "select a, COUNT(n.id) as newsCount FROM Author a LEFT JOIN a.news n GROUP BY a.id order by newsCount desc")
@Entity
public class Author implements Serializable {
    @Id
    @SequenceGenerator(name = "author_id_seq", sequenceName = "author_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_seq")
    private Long id;

    private String fullName;
    @ManyToMany(mappedBy = "authors")
    private List<News> news;

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

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public void addNews(News news) {
        if (!this.news.contains(news)) {
            this.news.add(news);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Author author = (Author) o;

        return fullName != null ? fullName.equals(author.fullName) : author.fullName == null;

    }

    @Override
    public int hashCode() {
        return fullName != null ? fullName.hashCode() : 0;
    }
}
