package com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.input.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * DTO expuesto por la API REST.
 */
@Schema(description = "Datos de precio devueltos por la API")
public record PriceResponse(
    @Schema(description = "Identificador del producto") Long productId,
    @Schema(description = "Identificador de la marca") Long brandId,
    @Schema(description = "Fecha de inicio de aplicación") LocalDateTime startDate,
    @Schema(description = "Fecha fin de aplicación") LocalDateTime endDate,
    @Schema(description = "Precio aplicado") BigDecimal price) {
}