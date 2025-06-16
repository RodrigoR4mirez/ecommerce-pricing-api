package com.inditex.pricing.ecommercepricingapi.infrastructure.rest.validation;

import static com.inditex.pricing.ecommercepricingapi.Constants.DATE_TIME_PATTERN;
import static com.inditex.pricing.ecommercepricingapi.Constants.VAL_APPLICATION_DATE_FORMAT;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import com.inditex.pricing.ecommercepricingapi.Constants;

/**
 * Valida que una cadena contenga una fecha con el formato esperado.
 */
@Documented
@Constraint(validatedBy = DateTimeValidator.class)
@Target({ METHOD, FIELD, PARAMETER, ANNOTATION_TYPE, TYPE_USE })
@Retention(RUNTIME)
public @interface ValidDateTime {

    String message() default VAL_APPLICATION_DATE_FORMAT;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Patrón de formato de fecha que debe cumplir el valor.
     */
    String pattern() default DATE_TIME_PATTERN;
}
