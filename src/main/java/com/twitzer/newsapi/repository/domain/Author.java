package com.twitzer.newsapi.repository.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import org.springframework.data.domain.Persistable;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author jschulz
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "NEWS_AUTHOR")
public class Author implements Persistable<Long> {

    private static final long serialVersionUID = 9180485233031144474L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRSTNAME", nullable = false, length = 300)
    private String firstname;

    @Column(name = "LASTNAME", nullable = false, length = 300)
    private String lastname;

    @ManyToMany(mappedBy = "authors", cascade = CascadeType.ALL)
    private List<Article> articles;

    @Column(name = "CREATED_ON", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    private LocalDateTime createdOn;

    @Column(name = "UPDATED_ON", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    private LocalDateTime updatedOn;


    public Author(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    @Override
    @Transient
    public boolean isNew() {
            return null == getId();
    }


    public void addArticle(Article article) {
        if(CollectionUtils.isEmpty(articles)) {
            this.articles = new ArrayList<>();
        }
        this.articles.add(article);
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

}
