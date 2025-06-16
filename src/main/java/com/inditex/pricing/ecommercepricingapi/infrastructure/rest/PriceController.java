package com.inditex.pricing.ecommercepricingapi.infrastructure.rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.inditex.pricing.ecommercepricingapi.Constants;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inditex.pricing.ecommercepricingapi.application.PriceService;
import com.inditex.pricing.ecommercepricingapi.domain.model.Price;
import com.inditex.pricing.ecommercepricingapi.infrastructure.rest.dto.PriceResponse;
import com.inditex.pricing.ecommercepricingapi.infrastructure.rest.mapper.PriceResponseMapper;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import com.inditex.pricing.ecommercepricingapi.infrastructure.rest.validation.ValidDateTime;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.inditex.pricing.ecommercepricingapi.Constants.DATE_TIME_PATTERN;
import static com.inditex.pricing.ecommercepricingapi.Constants.PATH_PRICES;
import static com.inditex.pricing.ecommercepricingapi.Constants.VAL_APPLICATION_DATE_NOTNULL;
import static com.inditex.pricing.ecommercepricingapi.Constants.VAL_BRAND_ID_POSITIVE;
import static com.inditex.pricing.ecommercepricingapi.Constants.VAL_PRODUCT_ID_POSITIVE;
import static com.inditex.pricing.ecommercepricingapi.Constants.PARAM_APPLICATION_DATE;

/**
 * Controlador REST encargado de exponer el servicio de consulta de precios.
 */
@RestController
@RequestMapping(PATH_PRICES)
@RequiredArgsConstructor
@Validated
@Slf4j
public class PriceController {

    private final PriceService priceService;
    private final PriceResponseMapper priceResponseMapper;

    /**
     * Endpoint de consulta de precio.
     *
     * @param brandId        identificador de la marca
     * @param productId      identificador del producto
     * @param applicationDate fecha de aplicación del precio
     * @return respuesta con el precio encontrado
     */
    @GetMapping(params = PARAM_APPLICATION_DATE)
    @Operation(summary = "Obtiene el precio aplicable a una fecha")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Precio encontrado"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "Precio no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public EntityModel<PriceResponse> getPrice(
            @PathVariable
            @Positive(message = VAL_BRAND_ID_POSITIVE)
            Long brandId,
            @PathVariable
            @Positive(message = VAL_PRODUCT_ID_POSITIVE)
            Long productId,
            @RequestParam
            @NotNull(message = VAL_APPLICATION_DATE_NOTNULL)
            @ValidDateTime
            String applicationDate) {
        log.info(Constants.LOG_REQUEST_PRICE, brandId, productId, applicationDate);
        LocalDateTime date = LocalDateTime.parse(applicationDate,
                DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
        Price price = priceService.getPrice(brandId, productId, date);
        PriceResponse response = priceResponseMapper.toResponse(price);
        EntityModel<PriceResponse> model = EntityModel.of(response);
        model.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PriceController.class)
                        .getPrice(brandId, productId, applicationDate))
                .withSelfRel());
        model.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PriceController.class)
                        .getPrices(brandId, productId))
                .withRel("prices"));
        return model;
    }

    /**
     * Endpoint que lista todos los precios disponibles para un producto.
     *
     * @param brandId   identificador de la marca
     * @param productId identificador del producto
     * @return colección de precios
     */
    @GetMapping
    @Operation(summary = "Lista los precios disponibles para un producto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Precios encontrados"),
            @ApiResponse(responseCode = "404", description = "Precios no encontrados"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public CollectionModel<PriceResponse> getPrices(
            @PathVariable
            @Positive(message = VAL_BRAND_ID_POSITIVE)
            Long brandId,
            @PathVariable
            @Positive(message = VAL_PRODUCT_ID_POSITIVE)
            Long productId) {
        log.info(Constants.LOG_REQUEST_PRICES, brandId, productId);
        java.util.List<PriceResponse> responses = priceResponseMapper.toResponseList(
                priceService.getPrices(brandId, productId));
        CollectionModel<PriceResponse> model = CollectionModel.of(responses);
        model.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PriceController.class)
                        .getPrices(brandId, productId))
                .withSelfRel());
        return model;
    }
}
