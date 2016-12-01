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
import javax.persistence.UniqueConstraint;
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

}
