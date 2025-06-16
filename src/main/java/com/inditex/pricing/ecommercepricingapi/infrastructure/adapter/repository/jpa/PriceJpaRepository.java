package com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.repository.jpa;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.inditex.pricing.ecommercepricingapi.Constants;

import static com.inditex.pricing.ecommercepricingapi.Constants.PARAM_APPLICATION_DATE;
import static com.inditex.pricing.ecommercepricingapi.Constants.PARAM_BRAND_ID;
import static com.inditex.pricing.ecommercepricingapi.Constants.PARAM_PRODUCT_ID;
import static com.inditex.pricing.ecommercepricingapi.Constants.QUERY_FIND_APPLICABLE_PRICES;
import static com.inditex.pricing.ecommercepricingapi.Constants.QUERY_FIND_ALL_PRICES;

/**
 * Repositorio JPA para la entidad {@link PriceJpaEntity}.
 */
public interface PriceJpaRepository extends JpaRepository<PriceJpaEntity, Long> {

    /**
     * Recupera todos los precios que se pueden aplicar ordenados por prioridad
     * descendente.
     *
     * @param brandId        identificador de la marca
     * @param productId      identificador del producto
     * @param applicationDate fecha de aplicación
     * @return lista de entidades ordenadas por prioridad
     */
    @Query(QUERY_FIND_APPLICABLE_PRICES)
    List<PriceJpaEntity> findApplicablePrices(@Param(PARAM_BRAND_ID) Long brandId,
                                              @Param(PARAM_PRODUCT_ID) Long productId,
                                              @Param(PARAM_APPLICATION_DATE) LocalDateTime applicationDate);

    /**
     * Recupera todos los precios para una marca y producto.
     *
     * @param brandId   identificador de la marca
     * @param productId identificador del producto
     * @return lista de precios
     */
    @Query(QUERY_FIND_ALL_PRICES)
    List<PriceJpaEntity> findAllPrices(@Param(PARAM_BRAND_ID) Long brandId,
                                       @Param(PARAM_PRODUCT_ID) Long productId);
}
