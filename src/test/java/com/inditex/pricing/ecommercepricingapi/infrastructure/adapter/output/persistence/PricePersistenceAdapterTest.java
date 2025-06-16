package com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.output.persistence;

import com.inditex.pricing.ecommercepricingapi.application.ports.output.PricePersistencePort;
import com.inditex.pricing.ecommercepricingapi.domain.model.Price;
import com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.output.persistence.mapper.PersistenceMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
    classes = {PricePersistenceAdapter.class,
        PersistenceMapper.class}))
class PricePersistenceAdapterTest {

  @Autowired
  private PricePersistencePort pricePersistencePort;


  @Test
  void finds_applicable_price() {
    LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T16:00:00");

    Price price = pricePersistencePort.findByBrandProductAndDate(1L, 35455L,
        applicationDate).orElseThrow();

    assertThat(price.price()).isEqualByComparingTo(BigDecimal.valueOf(25.45));
    assertThat(price.priceList()).isEqualTo(2L);
  }

  @Test
  void returns_empty_when_no_price_found_for_brand() {
    LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T16:00:00");

    Optional<Price> price = pricePersistencePort.findByBrandProductAndDate(999L,
        35455L, applicationDate);

    assertThat(price).isEmpty();
  }

  @Test
  void returns_empty_when_no_price_found_for_product() {
    LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T16:00:00");

    Optional<Price> price = pricePersistencePort.findByBrandProductAndDate(1L,
        999L, applicationDate);

    assertThat(price).isEmpty();
  }

  @Test
  void returns_empty_when_no_price_found_for_date() {
    LocalDateTime applicationDate = LocalDateTime.parse("2019-06-14T16:00:00");

    Optional<Price> price = pricePersistencePort.findByBrandProductAndDate(1L,
        35455L, applicationDate);

    assertThat(price).isEmpty();
  }

  @Test
  void finds_all_prices_for_brand_and_product() {
    List<Price> prices = pricePersistencePort.findByBrandAndProduct(1L,
        35455L);

    assertThat(prices).hasSize(4);
  }

  @Test
  void returns_empty_list_when_no_prices_found_for_brand() {
    List<Price> prices = pricePersistencePort.findByBrandAndProduct(999L,
        35455L);

    assertThat(prices).isEmpty();
  }

  @Test
  void returns_empty_list_when_no_prices_found_for_product() {
    List<Price> prices = pricePersistencePort.findByBrandAndProduct(1L,
        999L);

    assertThat(prices).isEmpty();
  }
}
