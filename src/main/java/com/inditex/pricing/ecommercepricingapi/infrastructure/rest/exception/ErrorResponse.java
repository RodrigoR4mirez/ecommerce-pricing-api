package com.inditex.pricing.ecommercepricingapi.infrastructure.rest.exception;

import java.util.List;

/**
 * DTO utilizado para devolver los errores de forma consistente.
 */
public record ErrorResponse(String code, String message, List<String> details) {
}

