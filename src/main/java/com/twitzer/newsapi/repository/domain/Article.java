package com.twitzer.newsapi.repository.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import org.hibernate.annotations.Type;
import org.springframework.data.domain.Persistable;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author jschulz
 */
@Data
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

}
