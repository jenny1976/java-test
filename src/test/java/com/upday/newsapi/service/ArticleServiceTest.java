package com.upday.newsapi.service;

import com.upday.newsapi.repository.ArticleRepository;
import com.upday.newsapi.repository.AuthorRepository;
import com.upday.newsapi.repository.KeywordRepository;
import com.upday.newsapi.repository.domain.Article;
import com.upday.newsapi.repository.domain.Author;
import com.upday.newsapi.repository.domain.Keyword;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;

/**
 *
 * @author jschulz
 */
public class ArticleServiceTest {

    private ArticleRepository articleRepository;
    private KeywordRepository keywordRepository;
    private AuthorRepository authorRepository;

    public ArticleServiceTest() {
    }

    @Before
    public void setUp() {
        articleRepository = mock(ArticleRepository.class);
        keywordRepository = mock(KeywordRepository.class);
        authorRepository = mock(AuthorRepository.class);
    }

    @After
    public void tearDown() {
        reset(articleRepository);
        reset(authorRepository);
        reset(keywordRepository);
    }

    @Test
    public void testCreateArticle() {
        Article dummy = new Article();
        dummy.setDescription("dummy description");
        dummy.setHeadline("dummy headline");
        dummy.setMainText("dummy text");
        dummy.setPublishedOn(LocalDate.now());

        Article dummy2 = dummy;
        dummy2.setId(33L);

        stub(articleRepository.save(dummy)).toReturn(dummy2);
        stub(articleRepository.findOne(33L)).toReturn(dummy2);

        final ArticleService toTest = new ArticleService(articleRepository, authorRepository, keywordRepository);
        Article result = toTest.createArticle(dummy);

        verify(articleRepository, times(1)).save(dummy);
        verify(articleRepository, times(1)).findOne(33L);
        MatcherAssert.assertThat(result, is(dummy2));
    }

    @Ignore
    @Test
    public void testCreateArticleWithAK() {
        LocalDate now = LocalDate.now();
        Article expected = new Article();
        expected.setId(33L);
        expected.setDescription("dummy description");
        expected.setHeadline("dummy headline");
        expected.setMainText("dummy text");
        expected.setPublishedOn(now);

        final Author a11 = new Author("f1", "l1");
        a11.setId(4L);
        final Author a21 = new Author("f2", "l2");
        a21.setId(5L);
        expected.addAuthor(a21);
        expected.addAuthor(a11);

        Keyword k11 = new Keyword("hot");
        k11.setId(9L);
        Keyword k21 = new Keyword("stuff");
        k21.setId(23L);
        expected.addKeyword(k11);
        expected.addKeyword(k21);

        // given
        Article input = new Article();
        input.setDescription("dummy description");
        input.setHeadline("dummy headline");
        input.setMainText("dummy text");
        input.setPublishedOn(now);

        List<Author> authors = new ArrayList<>(2);
        final Author a1 = new Author("f1", "l1");
        authors.add(a1);
        final Author a2 = new Author("f2", "l2");
        authors.add(a2);
        input.setAuthors(authors);

        Keyword k1 = new Keyword("hot");
        Keyword k2 = new Keyword("stuff");
        input.addKeyword(k1);
        input.addKeyword(k2);

        Article dummyWithoutAuth = new Article();
        dummyWithoutAuth.setDescription("dummy description");
        dummyWithoutAuth.setHeadline("dummy headline");
        dummyWithoutAuth.setMainText("dummy text");
        dummyWithoutAuth.setPublishedOn(input.getPublishedOn());

        Article dummyWithId = new Article();
        dummyWithId.setId(33L);
        dummyWithId.setDescription("dummy description");
        dummyWithId.setHeadline("dummy headline");
        dummyWithId.setMainText("dummy text");
        dummyWithId.setPublishedOn(input.getPublishedOn());

        when(articleRepository.save(input)).thenReturn(mock(Article.class));
        when(articleRepository.findOne(33L)).thenReturn(expected);

        when(authorRepository.exists(4L)).thenReturn(false);
        when(authorRepository.exists(5L)).thenReturn(false);
        when(authorRepository.save(a1)).thenReturn(a11);
        when(authorRepository.save(a2)).thenReturn(a21);
        when(authorRepository.findOne(4L)).thenReturn(a11);
        when(authorRepository.findOne(5L)).thenReturn(a21);

        when(keywordRepository.save(k1)).thenReturn(k11);
        when(keywordRepository.save(k2)).thenReturn(k21);
        when(keywordRepository.exists(9L)).thenReturn(false);
        when(keywordRepository.exists(23L)).thenReturn(false);
        when(keywordRepository.findOne(9L)).thenReturn(k11);
        when(keywordRepository.findOne(23L)).thenReturn(k21);

        // when
        final ArticleService toTest = new ArticleService(articleRepository, authorRepository, keywordRepository);
        Article result = toTest.createArticle(input);

        // then
        verify(articleRepository, times(1)).save(input);
        verify(articleRepository, times(3)).findOne(33L);
        verify(authorRepository, times(2)).save(any(Author.class));
        verify(keywordRepository, times(2)).save(any(Keyword.class));

        assertThat(result, is(expected));
    }

    @Test
    public void testUpdateArticle() {
    }

    @Test
    public void testDeleteArticle() {

        when(articleRepository.exists(1L)).thenReturn(false);
        when(articleRepository.exists(2L)).thenReturn(true);

        final ArticleService toTest = new ArticleService(articleRepository, authorRepository, keywordRepository);

        boolean res = toTest.deleteArticle(1L);
        boolean res2 = toTest.deleteArticle(2L);

        verify(articleRepository, times(1)).exists(1L);
        verify(articleRepository, times(1)).exists(2L);
    }

    @Test
    public void testFindOne() {
        Mockito.stub(articleRepository.findOne(1L)).toReturn(new Article());

        final ArticleService toTest = new ArticleService(articleRepository, authorRepository, keywordRepository);
        Article result = toTest.findOne(1L);

        assertThat(result, notNullValue());;
        verify(articleRepository, times(1)).findOne(1L);
    }

}
