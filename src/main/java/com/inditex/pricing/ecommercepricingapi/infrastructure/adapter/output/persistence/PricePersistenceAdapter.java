package com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.output.persistence;

import com.inditex.pricing.ecommercepricingapi.application.ports.output.PricePersistencePort;
import com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.output.persistence.mapper.PersistenceMapper;
import com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.output.persistence.repository.PriceRepository;
import com.inditex.pricing.ecommercepricingapi.utils.Constants;
import com.inditex.pricing.ecommercepricingapi.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * Adaptador que implementa el repositorio de dominio utilizando JPA.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class PricePersistenceAdapter implements PricePersistencePort {

  private final PriceRepository priceRepository;
  private final PersistenceMapper persistenceMapper;

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<Price> findByBrandProductAndDate(Long brandId, Long productId,
                                                   LocalDateTime applicationDate) {
    log.debug(Constants.LOG_DB_QUERY_PRICE, brandId, productId, applicationDate);
    return priceRepository.findApplicablePrices(brandId, productId, applicationDate)
        .stream().findFirst().map(persistenceMapper::toDomain);
  }

  @Override
  public List<Price> findByBrandAndProduct(Long brandId, Long productId) {
    log.debug(Constants.LOG_DB_QUERY_PRICES, brandId, productId);
    return priceRepository.findAllPrices(brandId, productId).stream().map(
        persistenceMapper::toDomain).toList();
  }
}
