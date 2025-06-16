package com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.repository.jpa;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

import com.inditex.pricing.ecommercepricingapi.Constants;
import org.springframework.stereotype.Repository;

import com.inditex.pricing.ecommercepricingapi.domain.model.Price;
import com.inditex.pricing.ecommercepricingapi.domain.repository.PriceRepository;
import com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.repository.jpa.PriceJpaRepository;
import com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.repository.jpa.PriceJpaMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adaptador que implementa el repositorio de dominio utilizando JPA.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class PriceRepositoryAdapter implements PriceRepository {

    private final PriceJpaRepository priceJpaRepository;
    private final PriceJpaMapper priceJpaMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Price> findByBrandProductAndDate(Long brandId, Long productId, LocalDateTime applicationDate) {
        log.debug(Constants.LOG_DB_QUERY_PRICE, brandId, productId, applicationDate);
        return priceJpaRepository.findApplicablePrices(brandId, productId, applicationDate)
                .stream()
                .findFirst()
                .map(priceJpaMapper::toDomain);
    }

    @Override
    public List<Price> findByBrandAndProduct(Long brandId, Long productId) {
        log.debug(Constants.LOG_DB_QUERY_PRICES, brandId, productId);
        return priceJpaRepository.findAllPrices(brandId, productId)
                .stream()
                .map(priceJpaMapper::toDomain)
                .toList();
    }
}
