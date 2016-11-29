package com.upday.newsapi.controller;

import com.upday.newsapi.model.RsArticle;
import com.upday.newsapi.model.RsAuthor;
import com.upday.newsapi.model.RsKeyword;
import com.upday.newsapi.repository.domain.Article;
import com.upday.newsapi.repository.domain.Author;
import com.upday.newsapi.repository.domain.Keyword;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jschulz
 */
public class ModelConverterTest {
    
    private RsArticle testRsArticle = new RsArticle();
    
    private Article dbArticle = new Article();
    
    public ModelConverterTest() {
    }
    
    @Before
    public void setUp() {
        testRsArticle.setHeadline("headline");
        testRsArticle.setId(1L);
        testRsArticle.setMainText("dummy maintext");
        testRsArticle.setPublishedOn(LocalDate.parse("2012-12-12"));
        testRsArticle.setTeaserText("dummy teaser text");
        
        RsAuthor rsAuthor = new RsAuthor(2L, "firstname1", "lastname1");
        RsAuthor rsAuthor2 = new RsAuthor(3L, "firstname2", "lastname2");
        List<RsAuthor> rsAuthors = new ArrayList<>(2);
        rsAuthors.add(rsAuthor);
        rsAuthors.add(rsAuthor2);
        testRsArticle.setAuthors(rsAuthors);
        
        RsKeyword rsKeyword = new RsKeyword(5L, "keyword1");
        RsKeyword rsKeyword2 = new RsKeyword(6L, "keyword2");
        List<RsKeyword> keywords = new ArrayList<>(2);
        keywords.add(rsKeyword);
        keywords.add(rsKeyword2);
        testRsArticle.setKeywords(keywords);
        
        dbArticle.setHeadline("headline");
        dbArticle.setDescription("description");
        dbArticle.setMainText("main text");
        dbArticle.setId(11L);
        dbArticle.setPublishedOn(LocalDate.parse("2012-12-12"));
        
        Author author = new Author("firstname1", "lastname1");
        author.setId(2L);
        Author author2 = new Author("firstname2", "lastname2");
        author2.setId(3L);
        List<Author> authors = new ArrayList<>(2);
        authors.add(author);
        authors.add(author2);
        dbArticle.setAuthors(authors);
        
        Keyword keyword = new Keyword( "keyword1");
        keyword.setId(5L);
        Keyword keyword2 = new Keyword( "keyword2");
        keyword2.setId(6L);
        List<Keyword> dbKeywords = new ArrayList<>(2);
        dbKeywords.add(keyword);
        dbKeywords.add(keyword2);
        dbArticle.setKeywords(dbKeywords);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testConvert_Article() {
        RsArticle result = ModelConverter.convert(dbArticle);
        
        RsArticle expected = new RsArticle();
        expected.setHeadline("headline");
        expected.setTeaserText("description");
        expected.setMainText("main text");
        expected.setId(11L);
        expected.setPublishedOn(LocalDate.parse("2012-12-12"));
        
        RsAuthor rsAuthor = new RsAuthor(2L, "firstname1", "lastname1");
        RsAuthor rsAuthor2 = new RsAuthor(3L, "firstname2", "lastname2");
        List<RsAuthor> rsAuthors = new ArrayList<>(2);
        rsAuthors.add(rsAuthor);
        rsAuthors.add(rsAuthor2);
        expected.setAuthors(rsAuthors);
        
        RsKeyword rsKeyword = new RsKeyword(5L, "keyword1");
        RsKeyword rsKeyword2 = new RsKeyword(6L, "keyword2");
        List<RsKeyword> keywords = new ArrayList<>(2);
        keywords.add(rsKeyword);
        keywords.add(rsKeyword2);
        expected.setKeywords(keywords);
        
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testConvertArticles() {
    }

    @Test
    public void testConvertToJpaArticle() {
        Article result = ModelConverter.convertToJpaArticle(testRsArticle);
        
        Article expected = new Article();
        expected.setDescription("dummy teaser text");
        expected.setHeadline("headline");
        expected.setId(1L);
        expected.setMainText("dummy maintext");
        expected.setPublishedOn(LocalDate.parse("2012-12-12"));
        
        Author author = new Author("firstname1", "lastname1");
        author.setId(2L);
        Author author2 = new Author("firstname2", "lastname2");
        author2.setId(3L);
        expected.addAuthor(author);
        expected.addAuthor(author2);
        Keyword keyword = new Keyword("keyword1");
        keyword.setId(5L);
        Keyword keyword2 = new Keyword("keyword2");
        keyword2.setId(6L);
        expected.addKeyword(keyword);
        expected.addKeyword(keyword2);
        
        Assert.assertEquals(expected, result);
    }
    
}
