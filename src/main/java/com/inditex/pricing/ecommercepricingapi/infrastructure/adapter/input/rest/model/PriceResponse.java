package com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.input.rest.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * DTO expuesto por la API REST.
 */
public record PriceResponse(Long productId,
                            Long brandId,
                            LocalDateTime startDate,
                            LocalDateTime endDate,
                            BigDecimal price) {
}
