package com.inditex.pricing.ecommercepricingapi.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

import com.inditex.pricing.ecommercepricingapi.application.impl.PriceServiceImpl;
import com.inditex.pricing.ecommercepricingapi.domain.model.Price;
import com.inditex.pricing.ecommercepricingapi.domain.repository.PriceRepository;
import com.inditex.pricing.ecommercepricingapi.infrastructure.rest.exception.PriceNotFoundException;
import com.inditex.pricing.ecommercepricingapi.Constants;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PriceServiceImplTest {

    private PriceService priceService;
    @Mock
    private PriceRepository priceRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        priceService = new PriceServiceImpl(priceRepository);
    }

    @Test
    void returns_price_when_found() {
        Price price = new Price(1L, LocalDateTime.now(), LocalDateTime.now(), 1L, 35455L, 0, BigDecimal.TEN, Constants.CURRENCY_EUR);
        when(priceRepository.findByBrandProductAndDate(1L, 35455L, price.startDate())).thenReturn(Optional.of(price));

        Price result = priceService.getPrice(1L, 35455L, price.startDate());

        assertThat(result).isEqualTo(price);
        verify(priceRepository).findByBrandProductAndDate(1L, 35455L, price.startDate());
    }

    @Test
    void returns_all_prices() {
        Price price = new Price(1L, LocalDateTime.now(), LocalDateTime.now(), 1L, 35455L, 0, BigDecimal.TEN, Constants.CURRENCY_EUR);
        when(priceRepository.findByBrandAndProduct(1L, 35455L)).thenReturn(List.of(price));

        List<Price> result = priceService.getPrices(1L, 35455L);

        assertThat(result).containsExactly(price);
        verify(priceRepository).findByBrandAndProduct(1L, 35455L);
    }

    @Test
    void throws_exception_when_no_prices_found() {
        when(priceRepository.findByBrandAndProduct(1L, 35455L)).thenReturn(List.of());

        assertThatThrownBy(() -> priceService.getPrices(1L, 35455L))
                .isInstanceOf(PriceNotFoundException.class);
    }

    @Test
    void throws_exception_when_not_found() {
        LocalDateTime now = LocalDateTime.now();
        when(priceRepository.findByBrandProductAndDate(1L, 35455L, now)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> priceService.getPrice(1L, 35455L, now))
                .isInstanceOf(PriceNotFoundException.class);
    }
}
