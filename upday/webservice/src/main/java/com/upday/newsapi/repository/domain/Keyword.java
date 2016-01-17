package com.upday.newsapi.repository.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Type;

import org.springframework.data.domain.Persistable;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author jschulz
 */
@Entity
@Table(name = "NEWS_KEYWORD",
        uniqueConstraints = { @UniqueConstraint(name = "unique_keyword_name", columnNames = {"NAME"}) }
)
public class Keyword implements Persistable<Long> {

    private static final long serialVersionUID = 4192010154194539491L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false, length = 300)
    private String name;

    @Column(name = "DESCRIPTION", length = 500)
    private String description;

    @ManyToMany(mappedBy = "keywords", cascade = CascadeType.ALL)
    private List<Article> articles;

    @Column(name = "CREATED_ON", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    private LocalDateTime createdOn;

    @Column(name = "UPDATED_ON", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    private LocalDateTime updatedOn;

    public Keyword(String name) {
        this.name = name;
    }

    public Keyword() {
        // default
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public void addArticle(Article article) {
        if(CollectionUtils.isEmpty(articles)) {
            this.articles = new ArrayList<>();
        }
        this.articles.add(article);
    }

    @Override
    @Transient
    public boolean isNew() {
            return null == getId();
    }

    @PrePersist
    public void setDefaultDates() {
        this.updatedOn = LocalDateTime.now();
        this.createdOn = LocalDateTime.now();
    }
    @PreUpdate
    public void updateUpdated() {
        this.updatedOn = LocalDateTime.now();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + Objects.hashCode(this.description);
        hash = 71 * hash + Objects.hashCode(this.articles);
        hash = 71 * hash + Objects.hashCode(this.createdOn);
        hash = 71 * hash + Objects.hashCode(this.updatedOn);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Keyword other = (Keyword) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.articles, other.articles)) {
            return false;
        }
        if (!Objects.equals(this.createdOn, other.createdOn)) {
            return false;
        }
        if (!Objects.equals(this.updatedOn, other.updatedOn)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Keyword{" + "id=" + id + ", name=" + name + ", description=" + description
                + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + '}';
    }


}
