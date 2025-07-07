package com.inditex.pricing.ecommercepricingapi.integration.infrastructure.adapter.input.rest;

import com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.input.rest.model.PriceResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.EntityModel;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pruebas de integración que validan los cinco escenarios de ejemplo
 * utilizando el endpoint REST real sin mocks.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PriceRestAdapterIntegrationSuccessTest {

  @LocalServerPort
  int port;

  @Autowired
  TestRestTemplate restTemplate;

  /**
   * Realiza una llamada al endpoint de precios para un producto concreto.
   *
   * @param date fecha y hora de consulta en formato ISO-8601
   * @return respuesta deserializada del servicio
   */
  private PriceResponse getPrice(String date) {
    String url = String.format(
        "http://localhost:%d/brands/1/products/35455/prices?applicationDate=%s",
        port, date);
    ResponseEntity<EntityModel<PriceResponse>> response = restTemplate.exchange(
        url,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<>() {});
    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    return response.getBody().getContent();
  }

  @Test
  @DisplayName("Escenario 1: petición a las 10:00 del 14/06/2020")
  void shouldReturnBasePriceAtMorningOfJune14() {
    PriceResponse price = getPrice("2020-06-14T10:00:00");
    assertThat(price).isNotNull();
    assertThat(price.productId()).isEqualTo(35455L);
    assertThat(price.brandId()).isEqualTo(1L);
    assertThat(price.startDate()).isEqualTo(LocalDateTime.parse("2020-06-14T00:00:00"));
    assertThat(price.endDate()).isEqualTo(LocalDateTime.parse("2020-12-31T23:59:59"));
    assertThat(price.price()).isEqualByComparingTo(new BigDecimal("35.50"));
  }

  @Test
  @DisplayName("Escenario 2: petición a las 16:00 del 14/06/2020")
  void shouldReturnPromoPriceAtAfternoonOfJune14() {
    PriceResponse price = getPrice("2020-06-14T16:00:00");
    assertThat(price).isNotNull();
    assertThat(price.productId()).isEqualTo(35455L);
    assertThat(price.brandId()).isEqualTo(1L);
    assertThat(price.startDate()).isEqualTo(LocalDateTime.parse("2020-06-14T15:00:00"));
    assertThat(price.endDate()).isEqualTo(LocalDateTime.parse("2020-06-14T18:30:00"));
    assertThat(price.price()).isEqualByComparingTo(new BigDecimal("25.45"));
  }

  @Test
  @DisplayName("Escenario 3: petición a las 21:00 del 14/06/2020")
  void shouldReturnBasePriceAtNightOfJune14() {
    PriceResponse price = getPrice("2020-06-14T21:00:00");
    assertThat(price).isNotNull();
    assertThat(price.productId()).isEqualTo(35455L);
    assertThat(price.brandId()).isEqualTo(1L);
    assertThat(price.startDate()).isEqualTo(LocalDateTime.parse("2020-06-14T00:00:00"));
    assertThat(price.endDate()).isEqualTo(LocalDateTime.parse("2020-12-31T23:59:59"));
    assertThat(price.price()).isEqualByComparingTo(new BigDecimal("35.50"));
  }

  @Test
  @DisplayName("Escenario 4: petición a las 10:00 del 15/06/2020")
  void shouldReturnMorningPriceOfJune15() {
    PriceResponse price = getPrice("2020-06-15T10:00:00");
    assertThat(price).isNotNull();
    assertThat(price.productId()).isEqualTo(35455L);
    assertThat(price.brandId()).isEqualTo(1L);
    assertThat(price.startDate()).isEqualTo(LocalDateTime.parse("2020-06-15T00:00:00"));
    assertThat(price.endDate()).isEqualTo(LocalDateTime.parse("2020-06-15T11:00:00"));
    assertThat(price.price()).isEqualByComparingTo(new BigDecimal("30.50"));
  }

  @Test
  @DisplayName("Escenario 5: petición a las 21:00 del 16/06/2020")
  void shouldReturnLatePriceOfJune16() {
    PriceResponse price = getPrice("2020-06-16T21:00:00");
    assertThat(price).isNotNull();
    assertThat(price.productId()).isEqualTo(35455L);
    assertThat(price.brandId()).isEqualTo(1L);
    assertThat(price.startDate()).isEqualTo(LocalDateTime.parse("2020-06-15T16:00:00"));
    assertThat(price.endDate()).isEqualTo(LocalDateTime.parse("2020-12-31T23:59:59"));
    assertThat(price.price()).isEqualByComparingTo(new BigDecimal("38.95"));
  }
}
