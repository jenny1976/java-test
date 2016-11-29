package com.upday.newsapi.repository;

import com.upday.newsapi.repository.domain.Author;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.stereotype.Repository;

/**
 * Repository to manage {@link Author} entities.
 *
 * @author jschulz
 */
@Repository
public interface AuthorRepository extends PagingAndSortingRepository<Author, Long> {


}
