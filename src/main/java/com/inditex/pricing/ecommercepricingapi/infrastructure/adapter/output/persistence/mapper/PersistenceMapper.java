package com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.output.persistence.mapper;

import com.inditex.pricing.ecommercepricingapi.domain.model.Price;
import com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.output.persistence.entity.PriceEntity;
import org.mapstruct.Mapper;

/**
 * Interfaz para el mapeo entre entidades JPA y objetos de dominio de precios.
 */
@Mapper(componentModel = "spring")
public interface PersistenceMapper {

  /**
   * Convierte una entidad JPA a un objeto del dominio.
   *
   * @param entity Entidad JPA de precio
   * @return Objeto Price del dominio
   */
  Price toDomain(PriceEntity entity);
}