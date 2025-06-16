package com.inditex.pricing.ecommercepricingapi.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/**
 * DTO utilizado para devolver los errores de forma consistente.
 */
@Schema(description = "Estructura de la respuesta de error")
public record ErrorResponse(
    @Schema(description = "Código HTTP del error") String code,
    @Schema(description = "Mensaje resumido del error") String message,
    @Schema(description = "Listado de detalles adicionales") List<String> details) {
}