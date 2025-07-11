package com.inditex.pricing.ecommercepricingapi.application.ports.output;

import com.inditex.pricing.ecommercepricingapi.domain.model.Price;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Contrato del repositorio encargado de acceder a los precios almacenados.
 */
public interface PricePersistencePort {

  /**
   * Busca un precio aplicable para la combinación de marca, producto y fecha.
   *
   * @param brandId         identificador de la marca
   * @param productId       identificador del producto
   * @param applicationDate fecha de aplicación
   * @return un {@link Price} si existe
   */
  Optional<Price> findByBrandProductAndDate(Long brandId,
                                            Long productId,
                                            LocalDateTime applicationDate);

  /**
   * Recupera todos los precios disponibles para una marca y producto.
   *
   * @param brandId   identificador de la marca
   * @param productId identificador del producto
   * @return lista de precios
   */
  java.util.List<Price> findByBrandAndProduct(Long brandId, Long productId);
}
