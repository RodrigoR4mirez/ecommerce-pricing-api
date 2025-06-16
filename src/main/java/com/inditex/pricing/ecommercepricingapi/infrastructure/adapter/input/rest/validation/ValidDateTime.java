package com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.input.rest.validation;

import static com.inditex.pricing.ecommercepricingapi.utils.Constants.DATE_TIME_PATTERN;
import static com.inditex.pricing.ecommercepricingapi.utils.Constants.VAL_APPLICATION_DATE_FORMAT;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * Valida que una cadena contenga una fecha con el formato esperado.
 */
@Documented
@Constraint(validatedBy = DateTimeValidator.class)
@Target({METHOD, FIELD, PARAMETER, ANNOTATION_TYPE, TYPE_USE})
@Retention(RUNTIME)
public @interface ValidDateTime {
  /**
   * Mensaje de error que se mostrará si la validación falla.
   */
  String message() default VAL_APPLICATION_DATE_FORMAT;

  /**
   * Grupos de validación a los que pertenece esta restricción.
   */
  Class<?>[] groups() default {};

  /**
   * Datos adicionales para la validación.
   */
  Class<? extends Payload>[] payload() default {};

  /**
   * Patrón de formato de fecha que debe cumplir el valor.
   */
  String pattern() default DATE_TIME_PATTERN;
}
