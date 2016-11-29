package com.upday.newsapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upday.newsapi.model.CreateArticle;
import com.upday.newsapi.model.UpdateArticle;
import com.upday.newsapi.repository.domain.Article;
import com.upday.newsapi.service.ArticleService;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import static org.hamcrest.core.StringContains.containsString;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 *
 * @author jschulz
 */

public class ArticlesControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mvc;
    private ArticleService articleService;

    @Before
    public void setUp() {
        articleService = mock(ArticleService.class);
        mvc = MockMvcBuilders.standaloneSetup(new ArticlesController(articleService)).build();
    }

    @Test
    public void testCreateArticle() throws Exception {
        System.out.println("----- createArticle");
        CreateArticle article = new CreateArticle(null, "subheadline", "text", Date.valueOf("2014-12-12"));

        Article dummy = new Article();
        dummy.setHeadline("headline");
        dummy.setDescription("subheadline");
        dummy.setMainText("text");
        dummy.setPublishedOn(LocalDate.parse("2014-12-12"));
        when(articleService.createArticle(anyObject())).thenReturn(dummy);

        // empty request
//        mvc.perform(MockMvcRequestBuilders.put("/articles/").contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(MockMvcResultMatchers.content().string(""));
//
//        // ivalid request content
//        mvc.perform(MockMvcRequestBuilders.put("/articles/").contentType(MediaType.APPLICATION_JSON)
//                .content("{}"))
//                .andExpect(status().isBadRequest())
//                .andExpect(MockMvcResultMatchers.content().string(contains("There are invalid arguments in 'newArticle':")));
//
//        // ivalid CreateArticle content
//        mvc.perform(MockMvcRequestBuilders.put("/articles/").contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(article)))
//                .andExpect(status().isBadRequest())
//                .andExpect(MockMvcResultMatchers.content().string(contains("There are invalid arguments in 'newArticle'.")));

        // valid request
        article.setHeadline("headline");
        mvc.perform(MockMvcRequestBuilders.put("/articles/").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(article))
                )
                .andExpect(status().isOk());

        verify(articleService, times(1)).createArticle(anyObject());

    }


    @Test
    public void testGetArticleByDateRange() throws Exception {
        System.out.println("----- getArticleByDateRange");

        // valid
        mvc.perform(MockMvcRequestBuilders.get("/articles/date/2013-12-12/2015-12-12").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // invalid date-format
        mvc.perform(MockMvcRequestBuilders.get("/articles/date/2013-122/2015-12-12").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(""));
        mvc.perform(MockMvcRequestBuilders.get("/articles/date/2013-12-12/15-12-12").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(""));

        // empty
        mvc.perform(MockMvcRequestBuilders.get("/articles/date/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        mvc.perform(MockMvcRequestBuilders.get("/articles/date/2013-12-12/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        // invalid date-range
        mvc.perform(MockMvcRequestBuilders.get("/articles/date/2013-12-12/2012-12-12").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("First Date has to be before the second one!"));

    }

    @Test
    public void invalid_update_article_empty_request() throws Exception {

        // empty request
        mvc.perform(MockMvcRequestBuilders.post("/articles/12").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(""));

        verify(articleService, never()).updateArticle(anyObject());

    }

    @Test
    public void invalid_update_article_with_invalid_request_content() throws Exception {

        // ivalid request content
        mvc.perform(MockMvcRequestBuilders.post("/articles/13").contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(containsString("There are invalid arguments in 'updateArticle':")));

        verify(articleService, never()).updateArticle(anyObject());
    }

    @Test
    public void invalid_update_article_with_invalid_content() throws Exception {

        UpdateArticle article = new UpdateArticle();
        article.setTeaserText("teaser");
        article.setMainText("main-text");
        article.setPublishedOn(Date.valueOf("2014-12-12"));

        // ivalid UpdateArticle content
        mvc.perform(MockMvcRequestBuilders.post("/articles/14").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(article)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(containsString("There are invalid arguments in 'updateArticle':")));

        verify(articleService, never()).updateArticle(anyObject());
    }

    @Test
    public void valid_update_article_request_nocontent() throws Exception {
        when(articleService.updateArticle(anyObject())).thenReturn(null);
        UpdateArticle article = new UpdateArticle();
        article.setTeaserText("teaser");
        article.setHeadline("headline");
        article.setMainText("main-text");
        article.setPublishedOn(Date.valueOf("2014-12-12"));

        // valid request
        mvc.perform(MockMvcRequestBuilders.post("/articles/15").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(article)))
                .andExpect(status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().string(""));

        verify(articleService, times(1)).updateArticle(anyObject());
    }

    @Test
    public void valid_update_article_request() throws Exception {
        when(articleService.updateArticle(anyObject())).thenReturn(mock(Article.class));

        UpdateArticle article = new UpdateArticle();
        article.setTeaserText("teaser");
        article.setHeadline("headline");
        article.setMainText("main-text");
        article.setPublishedOn(Date.valueOf("2014-12-12"));

        mvc.perform(MockMvcRequestBuilders.post("/articles/16").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(article)))
                .andExpect(status().isOk());

        verify(articleService, times(1)).updateArticle(anyObject());
    }

    @Test
    public void testDeleteArticle() throws Exception {
        System.out.println("----- deleteArticle");

        when(articleService.deleteArticle(1L)).thenReturn(true);

        // should be valid
        mvc.perform(MockMvcRequestBuilders.delete("/articles/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(202));

        verify(articleService, times(1)).deleteArticle(1L);


        reset(articleService);
        // valid, but entity not found
        mvc.perform(MockMvcRequestBuilders.delete("/articles/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));


    }

    @Test
    public void testGetArticle() throws Exception {
        System.out.println("----- getArticle");

        // valid
        when(articleService.findOne(1L)).thenReturn(new Article());
        mvc.perform(MockMvcRequestBuilders.get("/articles/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(articleService, times(1)).findOne(1L);
        reset(articleService);

        // empty
        mvc.perform(MockMvcRequestBuilders.get("/articles").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        // valid request but not found
        mvc.perform(MockMvcRequestBuilders.get("/articles/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    public void testGetArticleByAuthor() throws Exception {
        System.out.println("----- getArticleByAuthor");

        // empty
        mvc.perform(MockMvcRequestBuilders.get("/articles/author").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // valid
        mvc.perform(MockMvcRequestBuilders.get("/articles/author/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetArticleByKeyword() throws Exception {
        System.out.println("----- getArticleByKeyword");

        // empty
        mvc.perform(MockMvcRequestBuilders.get("/articles/search/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // valid
        mvc.perform(MockMvcRequestBuilders.get("/articles/search/berlin").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private ResultMatcher content(Matcher<String> containsString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
