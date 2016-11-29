package com.upday.newsapi.repository;

import com.upday.newsapi.repository.domain.Article;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.stereotype.Repository;

/**
 * Repository to manage {@link Article} entities.
 *
 * @author jschulz
 */
@Repository
public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {

    List<Article> findByAuthorsId(Long authorId);

    List<Article> findByKeywordsNameIgnoreCase(String keywordName);

    List<Article> findByKeywordsNameContainsIgnoreCase(String keywordName);

    List<Article> findByPublishedOnBetween(LocalDate from, LocalDate to);

}
