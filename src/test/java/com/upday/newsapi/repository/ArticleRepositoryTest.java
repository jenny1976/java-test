package com.upday.newsapi.repository;

import com.upday.newsapi.repository.domain.Article;
import com.upday.newsapi.repository.domain.Author;
import com.upday.newsapi.repository.domain.Keyword;
import java.time.LocalDate;
import java.util.List;
import javax.transaction.Transactional;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author jschulz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = DBConfig.class)
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private KeywordRepository keywordRepository;

    public ArticleRepositoryTest() {
    }

    @Before
    public void emptyDB() {
    }

    @Test
    public void testInsert() {

        Article a = createOne();

        articleRepository.save(a);

        Assert.assertEquals(3, articleRepository.count());
        Assert.assertEquals(2, a.getKeywords().size());
        Assert.assertEquals(2, a.getAuthors().size());
        Assert.assertNotNull(a.getId());

    }

    private Article createOne() {
        Article a = new Article();
        a.setHeadline("test headline");
        a.setDescription("test description");
        a.setMainText("test main text");
        a.setPublishedOn(LocalDate.now());
        a.addAuthor(authorRepository.save(new Author("first1", "last1")));
        a.addAuthor(authorRepository.save(new Author("first2", "last2")));
        a.addKeyword(keywordRepository.save(new Keyword("test")));
        a.addKeyword(keywordRepository.save(new Keyword("test kw2")));

        return a;
    }


    @Test
    public void testUpdate() {
        Article a = articleRepository.findOne(1L);

        a.setHeadline("changed headline");

        a = articleRepository.save(a);

        Assert.assertEquals("changed headline", a.getHeadline());
    }

    @Test
    public void testDelete() {
        Assert.assertEquals(2, articleRepository.count());

        articleRepository.delete(1L);

        Assert.assertFalse(articleRepository.exists(1L));
    }

    @Test
    public void testFindByAuthorsId() {
        Assert.assertEquals(2, articleRepository.count());

        List<Article> articles = articleRepository.findByAuthorsId(1L);

        Assert.assertNotNull(articles);
        Assert.assertThat(articles.size(), equalTo(1));
        Assert.assertThat(articles.get(0).getAuthors().size(), equalTo(2));
    }

    @Test
    public void testFindByKeywordsNameIgnoreCase() {

        List<Article> articles = articleRepository.findByKeywordsNameIgnoreCase("europa");

        Assert.assertNotNull(articles);
        Assert.assertThat(keywordRepository.count(), equalTo(4L));
        Assert.assertThat(articles.size(), equalTo(1));

        articles = articleRepository.findByKeywordsNameIgnoreCase("Europa");
        Assert.assertThat(articles.size(), equalTo(1));

        articles = articleRepository.findByKeywordsNameContainsIgnoreCase("EUROPa");
        Assert.assertThat(articles.size(), equalTo(1));
    }

    @Test
    public void testFindByPublishedOnBetween() {

        List<Article> articles = articleRepository.findByPublishedOnBetween(LocalDate.parse("2012-12-12"), LocalDate.now());
        Assert.assertThat(articles.size(), equalTo(2));

        articles = articleRepository.findByPublishedOnBetween(LocalDate.parse("2012-12-12"), LocalDate.parse("2014-12-12"));
        Assert.assertThat(articles.size(), equalTo(0));
    }

}
