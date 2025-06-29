package com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.input.rest;

import static com.inditex.pricing.ecommercepricingapi.utils.Constants.PATH_ALL_PRICES;
import static com.inditex.pricing.ecommercepricingapi.utils.Constants.DATE_TIME_PATTERN;
import static com.inditex.pricing.ecommercepricingapi.utils.Constants.PARAM_APPLICATION_DATE;
import static com.inditex.pricing.ecommercepricingapi.utils.Constants.PATH_PRICES;
import static com.inditex.pricing.ecommercepricingapi.utils.Constants.VAL_APPLICATION_DATE_NOTNULL;
import static com.inditex.pricing.ecommercepricingapi.utils.Constants.VAL_BRAND_ID_POSITIVE;
import static com.inditex.pricing.ecommercepricingapi.utils.Constants.VAL_PRODUCT_ID_POSITIVE;

import com.inditex.pricing.ecommercepricingapi.utils.Constants;
import com.inditex.pricing.ecommercepricingapi.application.ports.input.PriceServicePort;
import com.inditex.pricing.ecommercepricingapi.domain.model.Price;
import com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.input.rest.model.PriceResponse;
import com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.input.rest.mapper.PriceResponseMapper;
import com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.input.rest.validation.ValidDateTime;
import com.inditex.pricing.ecommercepricingapi.domain.model.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST encargado de exponer el servicio de consulta de precios.
 */
@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "Prices", description = "Operaciones relacionadas con los precios de productos")
public class PriceRestAdapter {

  private final PriceServicePort priceServicePort;
  private final PriceResponseMapper priceResponseMapper;

  /**
   * Endpoint de consulta de precio.
   *
   * @param brandId         identificador de la marca
   * @param productId       identificador del producto
   * @param applicationDate fecha de aplicación del precio
   * @return respuesta con el precio encontrado
   */
  @GetMapping(path = PATH_PRICES,
      params = PARAM_APPLICATION_DATE)
  @Operation(
      operationId = "getPriceByDate",
      summary = "Obtiene el precio aplicable a una fecha",
      description = "Devuelve el precio que debe aplicarse al producto indicado en la fecha proporcionada")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Precio encontrado",
          content = @Content(schema = @Schema(implementation = PriceResponse.class))),
      @ApiResponse(responseCode = "400", description = "Solicitud inválida",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "404", description = "Precio no encontrado",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
  public EntityModel<PriceResponse> getPrice(
      @Parameter(description = "Identificador de la marca")
      @PathVariable @Positive(message = VAL_BRAND_ID_POSITIVE) Long brandId,
      @Parameter(description = "Identificador del producto")
      @PathVariable @Positive(message = VAL_PRODUCT_ID_POSITIVE) Long productId,
      @Parameter(description = "Fecha y hora de aplicación del precio", example = "2020-06-14T10:00:00")
      @RequestParam @NotNull(message = VAL_APPLICATION_DATE_NOTNULL)
      @ValidDateTime String applicationDate) {

    log.info(Constants.LOG_REQUEST_PRICE, brandId, productId, applicationDate);
    LocalDateTime date = LocalDateTime.parse(applicationDate,
        DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    Price price = priceServicePort.getPrice(brandId, productId, date);
    PriceResponse response = priceResponseMapper.toResponse(price);
    EntityModel<PriceResponse> model = EntityModel.of(response);
    model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PriceRestAdapter.class)
        .getPrice(brandId, productId, applicationDate)).withSelfRel());
    model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PriceRestAdapter.class)
        .getAllPrices(brandId, productId)).withRel("prices"));
    return model;
  }

  /**
   * Endpoint que lista todos los precios disponibles para un producto.
   *
   * @param brandId   identificador de la marca
   * @param productId identificador del producto
   * @return colección de precios
   */
  @GetMapping(PATH_ALL_PRICES)
  @Operation(
      operationId = "listProductPrices",
      summary = "Lista los precios disponibles para un producto",
      description = "Recupera todos los precios configurados para el producto y la marca especificados")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Precios encontrados",
          content = @Content(array = @ArraySchema(schema = @Schema(implementation = PriceResponse.class)))),
      @ApiResponse(responseCode = "404", description = "Precios no encontrados",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "400", description = "Solicitud inválida",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
  public CollectionModel<PriceResponse> getAllPrices(
      @Parameter(description = "Identificador de la marca")
      @PathVariable @Positive(message = VAL_BRAND_ID_POSITIVE) Long brandId,
      @Parameter(description = "Identificador del producto")
      @PathVariable @Positive(message = VAL_PRODUCT_ID_POSITIVE) Long productId) {

    log.info(Constants.LOG_REQUEST_PRICES, brandId, productId);
    java.util.List<PriceResponse> responses = priceResponseMapper.toResponseList(
        priceServicePort.getPrices(brandId, productId));

    CollectionModel<PriceResponse> model = CollectionModel.of(responses);
    model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PriceRestAdapter.class)
        .getAllPrices(brandId, productId)).withSelfRel());

    return model;
  }
}