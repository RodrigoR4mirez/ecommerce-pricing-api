package com.inditex.pricing.ecommercepricingapi.application;

import java.time.LocalDateTime;

import com.inditex.pricing.ecommercepricingapi.domain.model.Price;

/**
 * Servicio de aplicación encargado de recuperar los precios aplicables a un
 * producto para una marca y fecha determinada.
 */
public interface PriceService {

    /**
     * Obtiene el precio que debe aplicarse a un producto en una fecha concreta
     * para una marca dada.
     *
     * @param brandId        identificador de la marca
     * @param productId      identificador del producto
     * @param applicationDate fecha de aplicación del precio
     * @return el precio aplicable
     */
    Price getPrice(Long brandId, Long productId, LocalDateTime applicationDate);

    /**
     * Obtiene todos los precios disponibles para una marca y producto.
     *
     * @param brandId   identificador de la marca
     * @param productId identificador del producto
     * @return lista de precios
     */
    java.util.List<Price> getPrices(Long brandId, Long productId);
}
