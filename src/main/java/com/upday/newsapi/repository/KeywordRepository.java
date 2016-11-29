package com.upday.newsapi.repository;

import com.upday.newsapi.repository.domain.Keyword;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.stereotype.Repository;

/**
 * Repository to manage {@link Keyword} entities.
 *
 * @author jschulz
 */
@Repository
public interface KeywordRepository extends PagingAndSortingRepository<Keyword, Long> {


}
