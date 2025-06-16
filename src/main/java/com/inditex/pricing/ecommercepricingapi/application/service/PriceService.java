package com.inditex.pricing.ecommercepricingapi.application.service;

import static com.inditex.pricing.ecommercepricingapi.utils.Constants.LOG_FETCH_PRICE;
import static com.inditex.pricing.ecommercepricingapi.utils.Constants.LOG_FETCH_PRICES;

import com.inditex.pricing.ecommercepricingapi.application.ports.input.PriceServicePort;
import com.inditex.pricing.ecommercepricingapi.application.ports.output.PricePersistencePort;
import com.inditex.pricing.ecommercepricingapi.domain.model.Price;
import com.inditex.pricing.ecommercepricingapi.domain.exception.PriceNotFoundException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación de {@link PriceServicePort} que delega en el repositorio la
 * búsqueda de precios y aplica la lógica de negocio necesaria.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PriceService implements PriceServicePort {

  private final PricePersistencePort pricePersistencePort;

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional(readOnly = true)
  public Price getPrice(Long brandId, Long productId, LocalDateTime applicationDate) {
    log.debug(LOG_FETCH_PRICE, brandId, productId, applicationDate);
    return pricePersistencePort.findByBrandProductAndDate(brandId, productId, applicationDate)
        .orElseThrow(PriceNotFoundException::new);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional(readOnly = true)
  public java.util.List<Price> getPrices(Long brandId, Long productId) {
    log.debug(LOG_FETCH_PRICES, brandId, productId);
    java.util.List<Price> prices = pricePersistencePort.findByBrandAndProduct(brandId, productId);
    if (prices.isEmpty()) {
      throw new PriceNotFoundException();
    }
    return prices;
  }
}
