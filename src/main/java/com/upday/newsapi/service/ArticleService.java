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
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author jschulz
 */
@Slf4j
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final AuthorRepository authorRepository;

    private final KeywordRepository keywordRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, AuthorRepository authorRepository,
            KeywordRepository keywordRepository) {
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
        this.keywordRepository = keywordRepository;
    }


    public Article createArticle(final Article article) {
        log.info("----------------- createArticle from: " + article);

        final List<Author> detachedAuthors = article.getAuthors();
        article.setAuthors(new ArrayList<>());
        final List<Keyword> detachedKeywords = article.getKeywords();
        article.setKeywords(new ArrayList<>());

        // first persist to get the PK
        Article newArticle = articleRepository.save(article);

        // save keywords and authors if new and attach to new article.
        saveAuthors(detachedAuthors, newArticle.getId());

        saveKeywords(detachedKeywords, newArticle.getId());

        return articleRepository.findOne(newArticle.getId());
    }

    public Article updateArticle(final Article input) {
        log.info("----------------- updateArticle from: " + input);
        Article toUpdate = articleRepository.findOne(input.getId());

        if(null == toUpdate) {
            return null;
        }

        final List<Author> detachedAuthors = input.getAuthors();
        final List<Keyword> detachedKeywords = input.getKeywords();

        toUpdate.setAuthors(new ArrayList<>()); // reset attributes
        toUpdate.setKeywords(new ArrayList<>());
        toUpdate.setDescription(input.getDescription());
        toUpdate.setHeadline(input.getHeadline());
        toUpdate.setMainText(input.getMainText());

        // merge
        articleRepository.save(toUpdate);

        // save keywords and authors.
        saveAuthors(detachedAuthors, toUpdate.getId());

        saveKeywords(detachedKeywords, toUpdate.getId());

        return articleRepository.findOne(toUpdate.getId());
    }

    public boolean deleteArticle(Long articleId) {
        log.info("----------------- delete article with id: " + articleId);
        if(articleRepository.exists(articleId)) {
            articleRepository.delete(articleId);
        } else {
            return false;
        }
        return true;
    }

    public Article findOne(final Long articleId) {
        log.info("----------------- find article with id: " + articleId);
        return articleRepository.findOne(articleId);
    }

    public List<Article> findByAuthorId(final Long authorId) {
        log.info("----------------- find articles by authorId: " + authorId);
        return articleRepository.findByAuthorsId(authorId);
    }

    public List<Article> findByKeywordName(final String searchKeyword) {
        log.info("----------------- find articles by keyword: " + searchKeyword);
        return articleRepository.findByKeywordsNameIgnoreCase(searchKeyword);
    }

    public List<Article> findByDateRange(final LocalDate from, final LocalDate to) {
        log.info("----------------- findByDateRange: " + from +" - "+ to);
        return articleRepository.findByPublishedOnBetween(from, to);
    }



    @Transactional
    private void saveKeywords(final List<Keyword> detachedKeywords, final Long articleId) {

        if(!CollectionUtils.isEmpty(detachedKeywords)) {

            Article entity = articleRepository.findOne(articleId);
            detachedKeywords.stream().map((keyword) -> {
                if(keyword.isNew()|| !authorRepository.exists(keyword.getId())) {
                    keyword = keywordRepository.save(keyword);
                }
                return keyword;
            }).forEach((keyword) -> {
                entity.addKeyword(keywordRepository.findOne(keyword.getId()));
            });
            articleRepository.save(entity);
        }
    }

    @Transactional
    private void saveAuthors(final List<Author> detachedAuthors, final Long articleId) {

        if(!CollectionUtils.isEmpty(detachedAuthors)) {

            Article article = articleRepository.findOne(articleId);
            detachedAuthors.stream().map((author) -> {
                if(author.isNew() || !authorRepository.exists(author.getId())) {
                    author = authorRepository.save(author);
                }
                return author;
            }).forEach((author) -> {
                Author saved = authorRepository.findOne(author.getId());
                article.addAuthor(saved);
            });
            articleRepository.save(article);
        }
    }

}
