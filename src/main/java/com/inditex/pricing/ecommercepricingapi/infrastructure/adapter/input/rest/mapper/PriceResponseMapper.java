package com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.input.rest.mapper;

import com.inditex.pricing.ecommercepricingapi.domain.model.Price;
import com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.input.rest.model.PriceResponse;
import org.mapstruct.Mapper;

import java.util.List;


/**
 * Interfaz para mapear entidades de dominio Price a DTOs PriceResponse.
 */
@Mapper(componentModel = "spring")
public interface PriceResponseMapper {

  /**
   * Convierte una entidad Price a un DTO PriceResponse.
   *
   * @param price Entidad de dominio con los datos del precio
   * @return DTO con los datos mapeados del precio
   */
  PriceResponse toResponse(Price price);

  /**
   * Convierte una lista de entidades Price a una lista de DTOs PriceResponse.
   *
   * @param prices Lista de entidades de dominio con los precios
   * @return Lista de DTOs con los precios mapeados
   */
  List<PriceResponse> toResponseList(List<Price> prices);
}