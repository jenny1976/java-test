package com.upday.newsapi;

import static org.hamcrest.Matchers.containsString;

import java.net.URL;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class HelloControllerIT {

    @LocalServerPort
    private int port;

    private URL base;
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
            this.base = new URL("http://localhost:" + port + "/health");
            template = new TestRestTemplate();
    }

    @Test
    public void getHello() throws Exception {
            ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
            assertThat(response.getBody(), containsString("UP"));
    }
}
