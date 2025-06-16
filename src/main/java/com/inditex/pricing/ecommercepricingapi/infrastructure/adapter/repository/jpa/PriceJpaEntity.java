package com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.repository.jpa;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.inditex.pricing.ecommercepricingapi.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.inditex.pricing.ecommercepricingapi.Constants.COLUMN_BRAND_ID;
import static com.inditex.pricing.ecommercepricingapi.Constants.COLUMN_END_DATE;
import static com.inditex.pricing.ecommercepricingapi.Constants.COLUMN_PRICE_LIST;
import static com.inditex.pricing.ecommercepricingapi.Constants.COLUMN_PRODUCT_ID;
import static com.inditex.pricing.ecommercepricingapi.Constants.COLUMN_START_DATE;
import static com.inditex.pricing.ecommercepricingapi.Constants.TABLE_PRICES;

/**
 * Entidad JPA que representa la tabla <code>prices</code>.
 */
@Entity
@Table(name = TABLE_PRICES)
@Getter
@Setter
@NoArgsConstructor
public class PriceJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = COLUMN_BRAND_ID, nullable = false)
    private Long brandId;

    @Column(name = COLUMN_START_DATE, nullable = false)
    private LocalDateTime startDate;

    @Column(name = COLUMN_END_DATE, nullable = false)
    private LocalDateTime endDate;

    @Column(name = COLUMN_PRICE_LIST, nullable = false)
    private Long priceList;

    @Column(name = COLUMN_PRODUCT_ID, nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer priority;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String currency;
}
