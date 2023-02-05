package com.biraj.tdd;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Test
    public void integration_test() {
        testRestTemplate.delete("/hotels/1");
    }
    @Test
    public void gets_near_by_hotels() {
        ResponseEntity<List> response = testRestTemplate.getForEntity("/hotels/search/{cityId}", List.class, "1");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List hotels = response.getBody();
        assert hotels != null;
        assertThat(hotels).isNotEmpty();
    }
}
