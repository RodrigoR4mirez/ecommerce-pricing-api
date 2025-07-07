package com.inditex.pricing.ecommercepricingapi.integration.infrastructure.adapter.input.rest;

import com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.input.rest.model.PriceResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.EntityModel;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PriceRestAdapterIntegrationSuccessTest {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate restTemplate;

    private PriceResponse getPrice(String date) {
        String url = String.format("http://localhost:%d/brands/1/products/35455/prices?applicationDate=%s", port, date);
        ResponseEntity<EntityModel<PriceResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        return response.getBody().getContent();
    }

    @Test
    void testScenario1() {
        PriceResponse price = getPrice("2020-06-14T10:00:00");
        assertThat(price).isNotNull();
        assertThat(price.productId()).isEqualTo(35455L);
        assertThat(price.brandId()).isEqualTo(1L);
        assertThat(price.startDate()).isEqualTo(LocalDateTime.parse("2020-06-14T00:00:00"));
        assertThat(price.endDate()).isEqualTo(LocalDateTime.parse("2020-12-31T23:59:59"));
        assertThat(price.price()).isEqualByComparingTo(new BigDecimal("35.50"));
    }

    @Test
    void testScenario2() {
        PriceResponse price = getPrice("2020-06-14T16:00:00");
        assertThat(price).isNotNull();
        assertThat(price.productId()).isEqualTo(35455L);
        assertThat(price.brandId()).isEqualTo(1L);
        assertThat(price.startDate()).isEqualTo(LocalDateTime.parse("2020-06-14T15:00:00"));
        assertThat(price.endDate()).isEqualTo(LocalDateTime.parse("2020-06-14T18:30:00"));
        assertThat(price.price()).isEqualByComparingTo(new BigDecimal("25.45"));
    }

    @Test
    void testScenario3() {
        PriceResponse price = getPrice("2020-06-14T21:00:00");
        assertThat(price).isNotNull();
        assertThat(price.productId()).isEqualTo(35455L);
        assertThat(price.brandId()).isEqualTo(1L);
        assertThat(price.startDate()).isEqualTo(LocalDateTime.parse("2020-06-14T00:00:00"));
        assertThat(price.endDate()).isEqualTo(LocalDateTime.parse("2020-12-31T23:59:59"));
        assertThat(price.price()).isEqualByComparingTo(new BigDecimal("35.50"));
    }

    @Test
    void testScenario4() {
        PriceResponse price = getPrice("2020-06-15T10:00:00");
        assertThat(price).isNotNull();
        assertThat(price.productId()).isEqualTo(35455L);
        assertThat(price.brandId()).isEqualTo(1L);
        assertThat(price.startDate()).isEqualTo(LocalDateTime.parse("2020-06-15T00:00:00"));
        assertThat(price.endDate()).isEqualTo(LocalDateTime.parse("2020-06-15T11:00:00"));
        assertThat(price.price()).isEqualByComparingTo(new BigDecimal("30.50"));
    }

    @Test
    void testScenario5() {
        PriceResponse price = getPrice("2020-06-16T21:00:00");
        assertThat(price).isNotNull();
        assertThat(price.productId()).isEqualTo(35455L);
        assertThat(price.brandId()).isEqualTo(1L);
        assertThat(price.startDate()).isEqualTo(LocalDateTime.parse("2020-06-15T16:00:00"));
        assertThat(price.endDate()).isEqualTo(LocalDateTime.parse("2020-12-31T23:59:59"));
        assertThat(price.price()).isEqualByComparingTo(new BigDecimal("38.95"));
    }
}

