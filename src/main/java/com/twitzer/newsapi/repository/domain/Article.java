package com.twitzer.newsapi.repository.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import org.hibernate.annotations.Type;
import org.springframework.data.domain.Persistable;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author jschulz
 */
@Entity
@Table(name = "NEWS_ARTICLE")
public class Article implements Persistable<Long> {

    private static final long serialVersionUID = 1651369642672635031L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "HEADLINE", nullable = false, length = 300)
    private String headline;

    @Column(name = "DESCRIPTION", length = 500)
    private String description;

    @Column(name = "TEXT", length = 3000)
    private String mainText;

    @Column(name = "CREATED_ON", nullable = false)
    @Type(type="org.hibernate.type.LocalDateTimeType")
    private LocalDateTime createdOn;

    @Column(name = "UPDATED_ON", nullable = false)
//    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    @Type(type="org.hibernate.type.LocalDateTimeType")
    private LocalDateTime updatedOn;

    @Column(name = "PUBLISHED_ON")
    @Type(type="org.hibernate.type.LocalDateType")
    private LocalDate publishedOn;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name="NEWS_ARTICLE_AUTHOR",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"ARTICLE_ID", "AUTHOR_ID"}, name = "UNIQUE_ARTICLE_AUTHOR")
        },
        joinColumns={
            @JoinColumn(name="ARTICLE_ID", referencedColumnName="ID")
        },
        inverseJoinColumns={
            @JoinColumn(name="AUTHOR_ID", referencedColumnName="ID")
        }
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Author> authors;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name="NEWS_ARTICLE_KEYWORD",
        uniqueConstraints = {
          @UniqueConstraint(columnNames = {"ARTICLE_ID", "KEYWORD_ID"}, name = "UNIQUE_ARTICLE_KEYWORD")
        },
        joinColumns={
            @JoinColumn(name="ARTICLE_ID", referencedColumnName="ID")
        },
        inverseJoinColumns={
            @JoinColumn(name="KEYWORD_ID", referencedColumnName="ID")
        }
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Keyword> keywords;

    public Article() {
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
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

    public LocalDate getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(LocalDate publishedOn) {
        this.publishedOn = publishedOn;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return this.id;
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

    /*
     * convenience methods.
     */
    public void addKeyword(final Keyword keyword) {
        if(CollectionUtils.isEmpty(keywords)) {
            keywords = new ArrayList<>();
        }
        keywords.add(keyword);
    }
    public void addAuthor(final Author author) {
        if(CollectionUtils.isEmpty(authors)) {
            authors = new ArrayList<>();
        }
        authors.add(author);
    }
    /**/

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.headline);
        hash = 17 * hash + Objects.hashCode(this.description);
        hash = 17 * hash + Objects.hashCode(this.mainText);
        hash = 17 * hash + Objects.hashCode(this.createdOn);
        hash = 17 * hash + Objects.hashCode(this.updatedOn);
        hash = 17 * hash + Objects.hashCode(this.publishedOn);
        hash = 17 * hash + Objects.hashCode(this.authors);
        hash = 17 * hash + Objects.hashCode(this.keywords);
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
        final Article other = (Article) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.headline, other.headline)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.mainText, other.mainText)) {
            return false;
        }
        if (!Objects.equals(this.createdOn, other.createdOn)) {
            return false;
        }
        if (!Objects.equals(this.updatedOn, other.updatedOn)) {
            return false;
        }
        if (!Objects.equals(this.publishedOn, other.publishedOn)) {
            return false;
        }
        if (!Objects.equals(this.authors, other.authors)) {
            return false;
        }
        if (!Objects.equals(this.keywords, other.keywords)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Article{" + "id=" + id + ", headline=" + headline + ", description="
                + description + ", mainText=" + mainText + ", createdOn=" + createdOn
                + ", updatedOn=" + updatedOn + ", publishedOn=" + publishedOn + ", authors="
                + authors + ", keywords=" + keywords + '}';
    }

}
