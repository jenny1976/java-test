package com.upday.newsapi.repository;

import com.upday.newsapi.repository.domain.Keyword;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 *
 * @author jschulz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = DBConfig.class)
public class KeywordRepositoryTest {

    @Autowired
    private KeywordRepository keywordRepository;

    @Test
    public void testKeywordNameUniqueConstraint() {
        Keyword k1 = new Keyword("test1");
        Keyword k2 = new Keyword("test1");

        keywordRepository.save(k1);
        keywordRepository.save(new Keyword("Test1"));

        org.junit.Assert.assertEquals(6, keywordRepository.count());

        try {
            keywordRepository.save(k2);
            Assert.isTrue(false, "DataIntegrityViolationException wasn't thrown!");
        } catch( DataIntegrityViolationException ex) {
            Assert.isTrue(true, "should be thrown.");
        }


    }

}
