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
import org.hibernate.annotations.Type;

import org.springframework.data.domain.Persistable;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author jschulz
 */
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

    public Author() {
        // default
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

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.firstname);
        hash = 59 * hash + Objects.hashCode(this.lastname);
        hash = 59 * hash + Objects.hashCode(this.articles);
        hash = 59 * hash + Objects.hashCode(this.createdOn);
        hash = 59 * hash + Objects.hashCode(this.updatedOn);
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
        final Author other = (Author) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.firstname, other.firstname)) {
            return false;
        }
        if (!Objects.equals(this.lastname, other.lastname)) {
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
        return "Author{" + "id=" + id + ", firstname=" + firstname + ", lastname=" 
                + lastname + ", createdOn=" + createdOn 
                + ", updatedOn=" + updatedOn + '}';
    }
}
