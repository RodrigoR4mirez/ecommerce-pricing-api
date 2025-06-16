package com.inditex.pricing.ecommercepricingapi.application.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inditex.pricing.ecommercepricingapi.application.PriceService;
import com.inditex.pricing.ecommercepricingapi.domain.model.Price;
import com.inditex.pricing.ecommercepricingapi.domain.repository.PriceRepository;
import com.inditex.pricing.ecommercepricingapi.infrastructure.rest.exception.PriceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.inditex.pricing.ecommercepricingapi.Constants;

import static com.inditex.pricing.ecommercepricingapi.Constants.LOG_FETCH_PRICE;
import static com.inditex.pricing.ecommercepricingapi.Constants.LOG_FETCH_PRICES;

/**
 * Implementación de {@link PriceService} que delega en el repositorio la
 * búsqueda de precios y aplica la lógica de negocio necesaria.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Price getPrice(Long brandId, Long productId, LocalDateTime applicationDate) {
        log.debug(LOG_FETCH_PRICE, brandId, productId, applicationDate);
        return priceRepository.findByBrandProductAndDate(brandId, productId, applicationDate)
                .orElseThrow(PriceNotFoundException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public java.util.List<Price> getPrices(Long brandId, Long productId) {
        log.debug(LOG_FETCH_PRICES, brandId, productId);
        java.util.List<Price> prices = priceRepository.findByBrandAndProduct(brandId, productId);
        if (prices.isEmpty()) {
            throw new PriceNotFoundException();
        }
        return prices;
    }
}
