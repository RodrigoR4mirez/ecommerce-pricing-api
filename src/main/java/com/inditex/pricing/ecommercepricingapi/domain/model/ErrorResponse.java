package com.inditex.pricing.ecommercepricingapi.domain.model;

import java.util.List;

/**
 * DTO utilizado para devolver los errores de forma consistente.
 */
public record ErrorResponse(String code, String message, List<String> details) {
}

