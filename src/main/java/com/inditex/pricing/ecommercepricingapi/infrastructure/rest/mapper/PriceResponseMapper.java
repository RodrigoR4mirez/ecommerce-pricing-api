package com.inditex.pricing.ecommercepricingapi.infrastructure.rest.mapper;

import org.mapstruct.Mapper;

import com.inditex.pricing.ecommercepricingapi.domain.model.Price;
import com.inditex.pricing.ecommercepricingapi.infrastructure.rest.dto.PriceResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PriceResponseMapper {
    PriceResponse toResponse(Price price);

    List<PriceResponse> toResponseList(List<Price> prices);
}
