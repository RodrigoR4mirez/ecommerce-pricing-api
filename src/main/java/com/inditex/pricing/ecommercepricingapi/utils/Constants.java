package com.inditex.pricing.ecommercepricingapi.utils;

/**
 * Contiene valores constantes utilizados en toda la aplicación.
 */
public final class Constants {
  private Constants() {
  }

  public static final String PATH_PRICES = "/brands/{brandId}/products/{productId}/prices";

  // Log messages
  public static final String LOG_FETCH_PRICE = "Fetching price for brandId={} productId={} date={}";
  public static final String LOG_REQUEST_PRICE =
      "Received price request brandId={} productId={} date={}";
  public static final String LOG_DB_QUERY_PRICE =
      "Querying database for price brandId={} productId={} date={}";
  public static final String LOG_FETCH_PRICES = "Fetching prices for brandId={} productId={}";
  public static final String LOG_REQUEST_PRICES = "Received prices request brandId={} productId={}";
  public static final String LOG_DB_QUERY_PRICES =
      "Querying database for prices brandId={} productId={}";
  public static final String LOG_HANDLE_EXCEPTION = "Handling exception {}";

  // Standard messages
  public static final String RESOURCE_NOT_FOUND = "Resource not found";
  public static final String VALIDATION_FAILED = "Validation failed";
  public static final String INVALID_PARAMETER = "Invalid format for parameter '%s'";
  public static final String PRICE_NOT_FOUND = "Price not found";
  public static final String INTERNAL_ERROR = "Ocurrió un error técnico";
  public static final String INTERNAL_ERROR_DETAIL = "Se generó un error genérico";

  // Validation message keys
  public static final String VAL_BRAND_ID_POSITIVE = "{validation.brandId.positive}";
  public static final String VAL_PRODUCT_ID_POSITIVE = "{validation.productId.positive}";
  public static final String VAL_APPLICATION_DATE_NOTNULL = "{validation.applicationDate.notnull}";
  public static final String VAL_APPLICATION_DATE_FORMAT = "{validation.applicationDate.format}";

  public static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
  public static final String CURRENCY_EUR = "EUR";

  // Database table and column names
  public static final String TABLE_PRICES = "prices";
  public static final String COLUMN_BRAND_ID = "brand_id";
  public static final String COLUMN_START_DATE = "start_date";
  public static final String COLUMN_END_DATE = "end_date";
  public static final String COLUMN_PRICE_LIST = "price_list";
  public static final String COLUMN_PRODUCT_ID = "product_id";

  // JPA query and parameters
  public static final String QUERY_FIND_APPLICABLE_PRICES =
      "SELECT p FROM PriceEntity p WHERE p.brandId = :brandId AND "
      + "p.productId = :productId " + "AND :applicationDate BETWEEN "
      + "p.startDate AND p.endDate ORDER BY p.priority DESC";
  public static final String QUERY_FIND_ALL_PRICES =
      "SELECT p FROM PriceEntity p WHERE p.brandId = "
      + ":brandId AND p.productId = :productId ORDER BY p.startDate";
  public static final String PARAM_BRAND_ID = "brandId";
  public static final String PARAM_PRODUCT_ID = "productId";
  public static final String PARAM_APPLICATION_DATE = "applicationDate";
}
