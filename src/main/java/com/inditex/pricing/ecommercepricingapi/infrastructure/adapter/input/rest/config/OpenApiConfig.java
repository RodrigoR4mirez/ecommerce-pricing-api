package com.inditex.pricing.ecommercepricingapi.infrastructure.adapter.input.rest.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de la documentación OpenAPI para Swagger.
 */
@Configuration
public class OpenApiConfig {

  @Value("${api.base-url}")
  private String apiBaseUrl;

  /**
   * Crea la configuración de OpenAPI utilizada por Swagger.
   *
   * @return instancia de {@link OpenAPI}
   */
  @Bean
  public OpenAPI ecommercePricingOpenApi() {
    return new OpenAPI()
        .addServersItem(new Server().url(apiBaseUrl).description("API Base URL"))
        .info(new Info()
            .title("Ecommerce Pricing API")
            .version("1.0.0")
            .description("API for retrieving applicable product prices")
            .contact(new Contact().name("Pricing Team").email("pricing@example.com"))
            .license(new License().name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0")));
  }
}
