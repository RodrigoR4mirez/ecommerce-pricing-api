package com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.input.rest.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Validador que comprueba si una cadena representa una fecha con el patrón indicado.
 */
public class DateTimeValidator implements ConstraintValidator<ValidDateTime, String> {

  private String pattern;

  @Override
  public void initialize(ValidDateTime annotation) {
    this.pattern = annotation.pattern();
  }

  /**
   * Verifica que el valor proporcionado se corresponda con el patrón de fecha configurado.
   *
   * @param value   cadena a validar
   * @param context contexto de validación
   * @return {@code true} si el formato es correcto o {@code null}; en caso contrario {@code false}
   */
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true; // La comprobación de null se gestiona por separado
    }
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
      LocalDateTime.parse(value, formatter);
      return true;
    } catch (DateTimeParseException ex) {
      return false;
    }
  }
}
