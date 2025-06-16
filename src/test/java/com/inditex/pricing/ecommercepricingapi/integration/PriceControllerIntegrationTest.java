package com.inditex.pricing.ecommercepricingapi.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.inditex.pricing.ecommercepricingapi.application.PriceService;
import com.inditex.pricing.ecommercepricingapi.Constants;
import com.inditex.pricing.ecommercepricingapi.domain.model.Price;
import com.inditex.pricing.ecommercepricingapi.infrastructure.rest.exception.PriceNotFoundException;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceService priceService;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_TIME_PATTERN);

    @BeforeEach
    void setUp() {
        // Test 1: 2020-06-14 10:00
        when(priceService.getPrice(1L, 35455L, LocalDateTime.parse("2020-06-14T10:00:00", formatter)))
            .thenReturn(new Price(1L, 
                      LocalDateTime.parse("2020-06-14T00:00:00", formatter), 
                      LocalDateTime.parse("2020-12-31T23:59:59", formatter), 
                      1L, 35455L, 0, new BigDecimal("35.50"), "EUR"));

        // Test 2: 2020-06-14 16:00
        when(priceService.getPrice(1L, 35455L, LocalDateTime.parse("2020-06-14T16:00:00", formatter)))
            .thenReturn(new Price(1L, 
                      LocalDateTime.parse("2020-06-14T15:00:00", formatter), 
                      LocalDateTime.parse("2020-06-14T18:30:00", formatter), 
                      2L, 35455L, 1, new BigDecimal("25.45"), "EUR"));

        // Test 3: 2020-06-14 21:00
        when(priceService.getPrice(1L, 35455L, LocalDateTime.parse("2020-06-14T21:00:00", formatter)))
            .thenReturn(new Price(1L, 
                      LocalDateTime.parse("2020-06-14T00:00:00", formatter), 
                      LocalDateTime.parse("2020-12-31T23:59:59", formatter), 
                      1L, 35455L, 0, new BigDecimal("35.50"), "EUR"));

        // Test 4: 2020-06-15 10:00
        when(priceService.getPrice(1L, 35455L, LocalDateTime.parse("2020-06-15T10:00:00", formatter)))
            .thenReturn(new Price(1L, 
                      LocalDateTime.parse("2020-06-15T00:00:00", formatter), 
                      LocalDateTime.parse("2020-06-15T11:00:00", formatter), 
                      3L, 35455L, 1, new BigDecimal("30.50"), "EUR"));

        // Test 5: 2020-06-16 21:00
        when(priceService.getPrice(1L, 35455L, LocalDateTime.parse("2020-06-16T21:00:00", formatter)))
            .thenReturn(new Price(1L, 
                      LocalDateTime.parse("2020-06-15T16:00:00", formatter), 
                      LocalDateTime.parse("2020-12-31T23:59:59", formatter), 
                      4L, 35455L, 1, new BigDecimal("38.95"), "EUR"));

        // List all prices
        List<Price> allPrices = Arrays.asList(
            new Price(1L, 
                      LocalDateTime.parse("2020-06-14T00:00:00", formatter), 
                      LocalDateTime.parse("2020-12-31T23:59:59", formatter), 
                      1L, 35455L, 0, new BigDecimal("35.50"), "EUR"),
            new Price(1L, 
                      LocalDateTime.parse("2020-06-14T15:00:00", formatter), 
                      LocalDateTime.parse("2020-06-14T18:30:00", formatter), 
                      2L, 35455L, 1, new BigDecimal("25.45"), "EUR"),
            new Price(1L, 
                      LocalDateTime.parse("2020-06-15T00:00:00", formatter), 
                      LocalDateTime.parse("2020-06-15T11:00:00", formatter), 
                      3L, 35455L, 1, new BigDecimal("30.50"), "EUR"),
            new Price(1L, 
                      LocalDateTime.parse("2020-06-15T16:00:00", formatter), 
                      LocalDateTime.parse("2020-12-31T23:59:59", formatter), 
                      4L, 35455L, 1, new BigDecimal("38.95"), "EUR")
        );
        when(priceService.getPrices(1L, 35455L)).thenReturn(allPrices);

        // Not found cases
        when(priceService.getPrices(1L, 99999L)).thenThrow(new PriceNotFoundException());
        when(priceService.getPrice(1L, 99999L, LocalDateTime.parse("2020-06-14T10:00:00", formatter)))
            .thenThrow(new PriceNotFoundException());
    }

    @Test
    @DisplayName("Test 1: 2020-06-14 10:00")
    void test1() throws Exception {
        mockMvc.perform(get(Constants.PATH_PRICES, 1, 35455)
                        .param(Constants.PARAM_APPLICATION_DATE, "2020-06-14T10:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.prices.href").exists());
    }

    @Test
    @DisplayName("Test 2: 2020-06-14 16:00")
    void test2() throws Exception {
        mockMvc.perform(get(Constants.PATH_PRICES, 1, 35455)
                        .param(Constants.PARAM_APPLICATION_DATE, "2020-06-14T16:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.price").value(25.45))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.prices.href").exists());
    }

    @Test
    @DisplayName("Test 3: 2020-06-14 21:00")
    void test3() throws Exception {
        mockMvc.perform(get(Constants.PATH_PRICES, 1, 35455)
                        .param(Constants.PARAM_APPLICATION_DATE, "2020-06-14T21:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.prices.href").exists());
    }

    @Test
    @DisplayName("Test 4: 2020-06-15 10:00")
    void test4() throws Exception {
        mockMvc.perform(get(Constants.PATH_PRICES, 1, 35455)
                        .param(Constants.PARAM_APPLICATION_DATE, "2020-06-15T10:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(3))
                .andExpect(jsonPath("$.price").value(30.50))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.prices.href").exists());
    }

    @Test
    @DisplayName("Test 5: 2020-06-16 21:00")
    void test5() throws Exception {
        mockMvc.perform(get(Constants.PATH_PRICES, 1, 35455)
                        .param(Constants.PARAM_APPLICATION_DATE, "2020-06-16T21:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(4))
                .andExpect(jsonPath("$.price").value(38.95))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.prices.href").exists());
    }

    @Test
    @DisplayName("List all prices")
    void listAllPrices() throws Exception {
        mockMvc.perform(get(Constants.PATH_PRICES, 1, 35455))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.priceResponseList").isArray())
                .andExpect(jsonPath("$._embedded.priceResponseList.length()").value(4))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    @DisplayName("List not found returns 404")
    void listNotFound() throws Exception {
        mockMvc.perform(get(Constants.PATH_PRICES, 1, 99999))
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
        given(priceService.getPrice(1L, 35455L, LocalDateTime.parse("2020-06-14T10:00:00")))
                .willThrow(new RuntimeException("boom"));

        mockMvc.perform(get(Constants.PATH_PRICES, 1, 35455)
                        .param(Constants.PARAM_APPLICATION_DATE, "2020-06-14T10:00:00"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value(Constants.INTERNAL_ERROR))
                .andExpect(jsonPath("$.details[0]").value(Constants.INTERNAL_ERROR_DETAIL));
    }
}
