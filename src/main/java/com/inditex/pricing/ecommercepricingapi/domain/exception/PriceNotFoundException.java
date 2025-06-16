package com.inditex.pricing.ecommercepricingapi.domain.exception;

import static com.inditex.pricing.ecommercepricingapi.utils.Constants.PRICE_NOT_FOUND;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Excepción lanzada cuando no se encuentra un precio para los criterios
 * solicitados.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PriceNotFoundException extends RuntimeException {

  /**
   * Crea la excepción con el mensaje por defecto.
   */
  public PriceNotFoundException() {
    super(PRICE_NOT_FOUND);
  }

  /**
   * Crea la excepción con un mensaje personalizado.
   *
   * @param message mensaje de error
   */
  public PriceNotFoundException(String message) {
    super(message);
  }
}
