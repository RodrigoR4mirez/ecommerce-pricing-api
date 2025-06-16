package com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.repository.jpa;

import org.mapstruct.Mapper;

import com.inditex.pricing.ecommercepricingapi.domain.model.Price;

@Mapper(componentModel = "spring")
public interface PriceJpaMapper {
    Price toDomain(PriceJpaEntity entity);
}
