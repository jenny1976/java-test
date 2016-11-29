package com.twitzer.newsapi.repository;

import com.twitzer.newsapi.repository.domain.Author;
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
