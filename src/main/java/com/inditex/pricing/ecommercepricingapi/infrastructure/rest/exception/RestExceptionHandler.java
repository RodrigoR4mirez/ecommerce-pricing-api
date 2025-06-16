package com.inditex.pricing.ecommercepricingapi.infrastructure.rest.exception;

import java.util.List;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.inditex.pricing.ecommercepricingapi.Constants;
import lombok.extern.slf4j.Slf4j;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import static com.inditex.pricing.ecommercepricingapi.Constants.INVALID_PARAMETER;
import static com.inditex.pricing.ecommercepricingapi.Constants.RESOURCE_NOT_FOUND;
import static com.inditex.pricing.ecommercepricingapi.Constants.VALIDATION_FAILED;

/**
 * Manejador global de excepciones para la capa REST.
 */
@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

    /**
     * Gestiona la excepción de precio no encontrado devolviendo un código 404.
     *
     * @param ex excepción lanzada
     * @return respuesta con el mensaje de error
     */
    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePriceNotFound(PriceNotFoundException ex) {
        log.warn(Constants.LOG_HANDLE_EXCEPTION, ex.toString());
        ErrorResponse error = new ErrorResponse(String.valueOf(HttpStatus.NOT_FOUND.value()),
                RESOURCE_NOT_FOUND,
                List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Gestiona los errores de validación de beans.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        log.warn(Constants.LOG_HANDLE_EXCEPTION, ex.toString());
        List<String> details = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        ErrorResponse error = new ErrorResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                VALIDATION_FAILED,
                details);
        return ResponseEntity.badRequest().body(error);
    }

    /**
     * Gestiona las violaciones de restricciones de validación.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        log.warn(Constants.LOG_HANDLE_EXCEPTION, ex.toString());
        List<String> details = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .toList();
        ErrorResponse error = new ErrorResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                VALIDATION_FAILED,
                details);
        return ResponseEntity.badRequest().body(error);
    }

    /**
     * Gestiona los errores cuando no es posible convertir un parámetro al tipo requerido.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.warn(Constants.LOG_HANDLE_EXCEPTION, ex.toString());
        String detail = String.format(INVALID_PARAMETER, ex.getName());
        ErrorResponse error = new ErrorResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                VALIDATION_FAILED,
                List.of(detail));
        return ResponseEntity.badRequest().body(error);
    }

    /**
     * Gestiona cualquier error no controlado devolviendo un código 500.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error(Constants.LOG_HANDLE_EXCEPTION, ex.toString(), ex);
        ErrorResponse error = new ErrorResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                Constants.INTERNAL_ERROR,
                List.of(Constants.INTERNAL_ERROR_DETAIL));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
