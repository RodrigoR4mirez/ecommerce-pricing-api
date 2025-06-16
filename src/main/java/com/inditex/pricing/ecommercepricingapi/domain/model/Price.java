package com.inditex.pricing.ecommercepricingapi.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad de dominio que representa el precio de un producto.
 */
public record Price(Long brandId,
                    LocalDateTime startDate,
                    LocalDateTime endDate,
                    Long priceList,
                    Long productId,
                    Integer priority,
                    BigDecimal price,
                    String currency) {
}
