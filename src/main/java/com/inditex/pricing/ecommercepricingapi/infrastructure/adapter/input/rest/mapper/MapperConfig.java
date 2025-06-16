package com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.input.rest.mapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    /**
     * Configuración del bean mapper para conversión de precios.
     *
     * @return Instancia del mapper para convertir entidades Price a DTOs PriceResponse
     */
    @Bean
    public PriceResponseMapper priceResponseMapper() {
        return new PriceResponseMapperImpl();
    }
}