package by.bsu.diplom.newshub.domain.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@NamedQuery(name = "Tag.findWithCount", query = "select t, COUNT(n.id) as newsCount FROM Tag t LEFT JOIN t.news n GROUP BY t.id order by newsCount desc")
@Entity
public class Tag implements Serializable {
    @Id
    @SequenceGenerator(name = "tag_id_seq", sequenceName = "tag_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_id_seq")
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<News> news;

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

    public Set<News> getNews() {
        return news;
    }

    public void setNews(Set<News> news) {
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

        Tag tag = (Tag) o;

        return name != null ? name.equals(tag.name) : tag.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
