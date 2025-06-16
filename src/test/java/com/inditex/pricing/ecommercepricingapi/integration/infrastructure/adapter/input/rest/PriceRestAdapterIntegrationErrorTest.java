package com.inditex.pricing.ecommercepricingapi.integration.infrastructure.adapter.input.rest;

import com.inditex.pricing.ecommercepricingapi.application.ports.input.PriceServicePort;
import com.inditex.pricing.ecommercepricingapi.domain.exception.PriceNotFoundException;
import com.inditex.pricing.ecommercepricingapi.domain.model.Price;
import com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.input.rest.PriceRestAdapter;
import com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.input.rest.mapper.PriceResponseMapper;
import com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.input.rest.model.PriceResponse;
import com.inditex.pricing.ecommercepricingapi.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.inditex.pricing.ecommercepricingapi.utils.Constants.PATH_ALL_PRICES;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PriceRestAdapter.class)
@Import(com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.input.rest.mapper.MapperConfig.class)
class PriceRestAdapterIntegrationErrorTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PriceServicePort priceServicePort;

  @MockBean
  private PriceResponseMapper priceResponseMapper;

  private static final DateTimeFormatter formatter = DateTimeFormatter
      .ofPattern(Constants.DATE_TIME_PATTERN);

  @BeforeEach
  void setUp() {
    // Configure priceResponseMapper mock
    when(priceResponseMapper.toResponse(any(Price.class))).thenAnswer(invocation -> {
      Price price = invocation.getArgument(0);
      return new PriceResponse(price.productId(), price.brandId(), price.startDate(), price.endDate(), price.price());
    });

    when(priceResponseMapper.toResponseList(anyList())).thenAnswer(invocation -> {
      List<Price> prices = invocation.getArgument(0);
      return prices.stream()
          .map(price -> new PriceResponse(price.productId(), price.brandId(), price.startDate(), price.endDate(), price.price()))
          .collect(java.util.stream.Collectors.toList());
    });

    when(priceServicePort.getPrices(1L, 99999L))
        .thenThrow(new PriceNotFoundException());
    when(priceServicePort.getPrice(1L, 99999L,
        LocalDateTime.parse("2020-06-14T10:00:00", formatter)))
        .thenThrow(new PriceNotFoundException());
  }

  @Test
  @DisplayName("List not found returns 404")
  void listNotFound() throws Exception {
    mockMvc.perform(get(PATH_ALL_PRICES, 1, 99999))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value(Constants.RESOURCE_NOT_FOUND))
        .andExpect(jsonPath("$.details[0]").value(Constants.PRICE_NOT_FOUND));
  }

  @Test
  @DisplayName("Validation error when brandId is negative")
  void invalidBrandId() throws Exception {
    mockMvc.perform(get(Constants.PATH_PRICES, -1, 35455)
            .param(Constants.PARAM_APPLICATION_DATE, "2020-06-14T10:00:00"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(Constants.VALIDATION_FAILED));
  }

  @Test
  @DisplayName("Validation error when brandId is not a number")
  void invalidBrandIdType() throws Exception {
    mockMvc.perform(get(Constants.PATH_PRICES, "abc", 35455)
            .param(Constants.PARAM_APPLICATION_DATE, "2020-06-14T10:00:00"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(Constants.VALIDATION_FAILED));
  }

  @Test
  @DisplayName("Validation error when applicationDate format is wrong")
  void invalidApplicationDateFormat() throws Exception {
    mockMvc.perform(get(Constants.PATH_PRICES, 1, 35455)
            .param(Constants.PARAM_APPLICATION_DATE, "2020/06/14"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(Constants.VALIDATION_FAILED));
  }

  @Test
  @DisplayName("Price not found returns standard error")
  void priceNotFound() throws Exception {
    mockMvc.perform(get(Constants.PATH_PRICES, 1, 99999)
            .param(Constants.PARAM_APPLICATION_DATE, "2020-06-14T10:00:00"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value(Constants.RESOURCE_NOT_FOUND))
        .andExpect(jsonPath("$.details[0]").value(Constants.PRICE_NOT_FOUND));
  }

  @Test
  @DisplayName("Unhandled exception returns 500")
  void unhandledException() throws Exception {
    given(priceServicePort.getPrice(1L, 35455L,
        LocalDateTime.parse("2020-06-14T10:00:00", formatter)))
        .willThrow(new RuntimeException("boom"));

    mockMvc.perform(get(Constants.PATH_PRICES, 1, 35455)
            .param(Constants.PARAM_APPLICATION_DATE, "2020-06-14T10:00:00"))
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.message").value(Constants.INTERNAL_ERROR))
        .andExpect(jsonPath("$.details[0]").value(Constants.INTERNAL_ERROR_DETAIL));
  }

}
