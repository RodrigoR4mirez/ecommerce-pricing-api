package com.inditex.pricing.ecommercepricingapi.infrastructure.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * DTO expuesto por la API REST.
 */
public record PriceResponse(Long productId,
                            Long brandId,
                            Long priceList,
                            LocalDateTime startDate,
                            LocalDateTime endDate,
                            BigDecimal price,
                            String currency) {
}
