package com.twitzer.newsapi.controller;

import com.twitzer.newsapi.model.CreateArticle;
import com.twitzer.newsapi.repository.domain.Article;
import com.twitzer.newsapi.model.RsArticle;
import com.twitzer.newsapi.model.UpdateArticle;
import com.twitzer.newsapi.repository.domain.Keyword;
import com.twitzer.newsapi.service.ArticleService;

import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles Requests for the News-Article Service.
 *
 * @author jschulz
 */
@Slf4j
@RestController
@RequestMapping(
        value = "/articles",
        produces = { "application/json" },
        consumes = { "application/json" }
)
public class ArticlesController {

    private final ArticleService articleService;

    @Autowired
    public ArticlesController(final ArticleService articleService) {
        this.articleService = articleService;
    }


    /**
     * Create a new Article, and if needed also new Authors and Keywords from
     * the given {@link CreateArticle}.
     *
     * @param   newArticle  the input
     * @param   validationResult    result from bean-validation
     * @return  new created {@link RsArticle} and HttpStatusCode
     */
    @PutMapping( value = "/")
    public ResponseEntity<RsArticle> createArticle(final @RequestBody @Valid CreateArticle newArticle,
            final BindingResult validationResult ) {
        if (validationResult.hasErrors()) {
            log.error("There are invalid arguments in 'newArticle': {}", validationResult.getAllErrors());
            throw new IllegalArgumentException("There are invalid arguments in 'newArticle': " + validationResult.getAllErrors());
        }

        ResponseEntity<RsArticle> response;
        try {
            final Article article = articleService.createArticle(ModelConverter.convertToJpaArticle(newArticle));
            response = new ResponseEntity<>(ModelConverter.convert(article), HttpStatus.OK);
        } catch (DataIntegrityViolationException dive) {
            log.error("article couldn't be saved: " + dive);
            response = new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return response;
    }

    /**
     * Updates an Article by given article.id from the data
     * {@link UpdateArticle}.
     *
     * @param articleId         id of the article to change.
     * @param updateArticle     the input
     * @param validationResult  result from bean-validation
     * @return the updated {@link RsArticle}
     */
    @PostMapping( value = "/{articleId}" )
    public ResponseEntity<RsArticle> updateArticle(final @PathVariable("articleId") Long articleId,
            @RequestBody @Valid UpdateArticle updateArticle, final BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            log.error("There are invalid arguments in 'updateArticle': {}", validationResult.getAllErrors());
            throw new IllegalArgumentException("There are invalid arguments in 'updateArticle': " + validationResult.getAllErrors());
        }

        final Article article = articleService.updateArticle(ModelConverter.convertToJpaArticle(updateArticle, articleId));

        ResponseEntity<RsArticle> response;
        if (article == null) {
            response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseEntity<>(ModelConverter.convert(article), HttpStatus.OK);
        }

        return response;
    }

    /**
     * Delete an article by id-parameter.
     *
     * @param articleId     article.id
     * @return
     */
    @DeleteMapping( value = "/{articleId}" )
    public ResponseEntity deleteArticle(final @PathVariable("articleId") Long articleId) {

        boolean success = articleService.deleteArticle(articleId);
        if (!success) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * Get an {@link RsArticle} by a given id.
     *
     * @param   articleId     article.id
     * @return  the {@link RsArticle}
     */
    @GetMapping( value = "/{articleId}" )
    public ResponseEntity<RsArticle> getArticle(final @PathVariable("articleId") Long articleId) {

        final Article dbArticle = articleService.findOne(articleId);

        ResponseEntity<RsArticle> response;
        if (dbArticle == null) {
            response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseEntity<>(ModelConverter.convert(dbArticle), HttpStatus.OK);
        }
        return response;
    }

    /**
     * Get a List of {@link RsArticle}s by a given authorId.
     *
     * @param   authorId    an author.id
     * @return  an {@link RsArticle} List
     */
    @GetMapping( value = "/author/{authorId}" )
    public @ResponseBody List<RsArticle> getArticlesByAuthor(final @PathVariable("authorId") Long authorId) {

        final List<Article> articles = articleService.findByAuthorId(authorId);
        return ModelConverter.convertArticles(articles);
    }

    /**
     * the given Date Objects must be in ISO-8601 format: yyyy-MM-dd
     * e.g. '2011-06-23'
     *
     * @param fromDate  start-Date, mandatory parameter
     * @param toDate    end-Date, mandatory parameter
     *
     * @return  an {@link RsArticle} List
     */
    @GetMapping( value = "/date/{from}/{to}" )
    public @ResponseBody List<RsArticle> getArticlesByDateRange(
            final @PathVariable("from") @DateTimeFormat(iso=ISO.DATE) LocalDate fromDate,
            final @PathVariable("to") @DateTimeFormat(iso=ISO.DATE) LocalDate toDate) {

        List<RsArticle> result;

        if (fromDate.isBefore(toDate)) {

            final List<Article> articles = articleService.findByDateRange(fromDate, toDate);
            result = ModelConverter.convertArticles(articles);
        } else {
            throw new IllegalArgumentException("First Date has to be before the second one!");
        }
        return result;
    }

    /**
     * Search the {@link Keyword}.name attribute, return the suitable Articles.
     *
     * @param searchKeyword     the keyword.name
     * @return  an {@link RsArticle} List
     */
    @GetMapping( value = "/search/{searchKeyword}" )
    public @ResponseBody List<RsArticle> getArticlesByKeyword(final @PathVariable("searchKeyword") String searchKeyword) {

        final List<Article> articles = articleService.findByKeywordName(searchKeyword);
        return ModelConverter.convertArticles(articles);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) throws Exception {
	return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
