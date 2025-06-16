package com.inditex.pricing.ecommercepricingapi.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.inditex.pricing.ecommercepricingapi.domain.model.Price;
import com.inditex.pricing.ecommercepricingapi.domain.repository.PriceRepository;
import com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.repository.jpa.PriceRepositoryAdapter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(PriceRepositoryAdapter.class)
class PriceRepositoryAdapterTest {

    @Autowired
    private PriceRepository priceRepository;


    @Test
    void finds_applicable_price() {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T16:00:00");

        Price price = priceRepository.findByBrandProductAndDate(1L, 35455L, applicationDate).orElseThrow();

        assertThat(price.price()).isEqualByComparingTo(BigDecimal.valueOf(25.45));
        assertThat(price.priceList()).isEqualTo(2L);
    }

    @Test
    void finds_all_prices_for_brand_and_product() {
        java.util.List<Price> prices = priceRepository.findByBrandAndProduct(1L, 35455L);

        assertThat(prices).hasSize(4);
    }
}
