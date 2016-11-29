package com.upday.newsapi.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * @author jschulz
 */
public class UpdateArticle implements Serializable {
    
    private static final long serialVersionUID = -5829600795638058903L;
    

    @NotBlank
    private String headline;

    @NotBlank
    private String teaserText;
    
    @NotBlank
    private String mainText;
    
    @NotNull
    private Date publishedOn;
    
    private List<RsAuthor> authors;
    private List<RsKeyword> keywords;

    public UpdateArticle() {
        // default
    }

    public UpdateArticle(String headline, String teaserText, String mainText, Date publishedOn) {
        this.headline = headline;
        this.teaserText = teaserText;
        this.mainText = mainText;
        this.publishedOn = publishedOn;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getTeaserText() {
        return teaserText;
    }

    public void setTeaserText(String teaserText) {
        this.teaserText = teaserText;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public LocalDate publishedOnAsLocalDate() {
        return LocalDateTime.ofInstant(this.publishedOn.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }

    public Date getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(Date publishedOn) {
        this.publishedOn = publishedOn;
    }

    public List<RsAuthor> getAuthors() {
        return authors;
    }

    public void setAuthors(List<RsAuthor> authors) {
        this.authors = authors;
    }

    public List<RsKeyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<RsKeyword> keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "UpdateArticle{ headline=" + headline + ", teaserText=" 
                + teaserText + ", mainText=" + mainText + ", publishedOn=" + publishedOn 
                + ", authors=" + authors + ", keywords=" + keywords + '}';
    }
    
}
