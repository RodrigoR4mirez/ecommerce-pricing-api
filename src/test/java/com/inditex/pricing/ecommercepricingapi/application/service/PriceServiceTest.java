package com.inditex.pricing.ecommercepricingapi.application.service;

import com.inditex.pricing.ecommercepricingapi.application.ports.input.PriceServicePort;
import com.inditex.pricing.ecommercepricingapi.application.ports.output.PricePersistencePort;
import com.inditex.pricing.ecommercepricingapi.domain.exception.PriceNotFoundException;
import com.inditex.pricing.ecommercepricingapi.domain.model.Price;
import com.inditex.pricing.ecommercepricingapi.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PriceServiceTest {

  private PriceServicePort priceServicePort;
  @Mock
  private PricePersistencePort pricePersistencePort;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    priceServicePort = new PriceService(pricePersistencePort);
  }

  @Test
  void returns_price_when_found() {
    Price price = new Price(1L, LocalDateTime.now(), LocalDateTime.now(),
        1L, 35455L, 0, BigDecimal.TEN, Constants.CURRENCY_EUR);
    when(pricePersistencePort.findByBrandProductAndDate(1L, 35455L,
        price.startDate())).thenReturn(Optional.of(price));

    Price result = priceServicePort.getPrice(1L, 35455L, price.startDate());

    assertThat(result).isEqualTo(price);
    verify(pricePersistencePort).findByBrandProductAndDate(1L, 35455L,
        price.startDate());
  }

  @Test
  void returns_all_prices() {
    Price price = new Price(1L, LocalDateTime.now(), LocalDateTime.now(),
        1L, 35455L, 0, BigDecimal.TEN, Constants.CURRENCY_EUR);
    when(pricePersistencePort.findByBrandAndProduct(1L, 35455L))
        .thenReturn(List.of(price));

    List<Price> result = priceServicePort.getPrices(1L, 35455L);

    assertThat(result).containsExactly(price);
    verify(pricePersistencePort).findByBrandAndProduct(1L, 35455L);
  }

  @Test
  void throws_exception_when_no_prices_found() {
    when(pricePersistencePort.findByBrandAndProduct(1L, 35455L))
        .thenReturn(List.of());

    assertThatThrownBy(() -> priceServicePort.getPrices(1L, 35455L))
        .isInstanceOf(PriceNotFoundException.class);
  }

  @Test
  void throws_exception_when_not_found() {
    LocalDateTime now = LocalDateTime.now();
    when(pricePersistencePort.findByBrandProductAndDate(1L, 35455L, now))
        .thenReturn(Optional.empty());

    assertThatThrownBy(() -> priceServicePort.getPrice(1L, 35455L, now))
        .isInstanceOf(PriceNotFoundException.class);
  }
}
