# Ecommerce Pricing API

Este proyecto implementa un servicio REST creado con **Spring Boot** que permite consultar el precio aplicable a un producto en una fecha concreta.

## Descripción general

La aplicación expone dos endpoints:

- `GET /brands/{brandId}/products/{productId}/prices?applicationDate=yyyy-MM-dd'T'HH:mm:ss`
  Devuelve el precio que corresponde al producto y marca indicados en la fecha solicitada.
- `GET /brands/{brandId}/products/{productId}/all-prices`
  Lista todos los precios configurados para ese producto y cadena.

La base de datos utilizada es **H2 en memoria**. Durante el arranque se cargan los registros de ejemplo proporcionados en el enunciado.

```sql
INSERT INTO prices (brand_id, start_date, end_date, price_list, product_id, priority, price, currency) VALUES
(1, '2020-06-14 00:00:00', '2020-12-31 23:59:59', 1, 35455, 0, 35.50, 'EUR'),
(1, '2020-06-14 15:00:00', '2020-06-14 18:30:00', 2, 35455, 1, 25.45, 'EUR'),
(1, '2020-06-15 00:00:00', '2020-06-15 11:00:00', 3, 35455, 1, 30.50, 'EUR'),
(1, '2020-06-15 16:00:00', '2020-12-31 23:59:59', 4, 35455, 1, 38.95, 'EUR');
```

## Requisitos cumplidos

- **Parámetros de entrada**: se validan `brandId` y `productId` como numéricos positivos y `applicationDate` sigue el formato `yyyy-MM-dd'T'HH:mm:ss`.
- **Persistencia en H2**: se inicializa una tabla `prices` con la información anterior.
- **Lógica de negocio**: al solicitar un precio se selecciona el registro con mayor prioridad cuyo rango de fechas contiene la fecha indicada.
- **Respuestas al usuario**: la API devuelve objetos de dominio sencillos (`PriceResponse`) o mensajes de error estandarizados sin trazas técnicas.
- **Casos de prueba**: existen pruebas automáticas que verifican los cinco escenarios solicitados y las validaciones de parámetros.

## Uso

1. Compilar y arrancar la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```
2. Realizar peticiones siguiendo las URLs anteriores. Ejemplo:
   ```bash
   curl "http://localhost:8080/brands/1/products/35455/prices?applicationDate=2020-06-14T10:00:00"
   ```
   Respuesta simplificada:
   ```json
   {
     "productId": 35455,
     "brandId": 1,
     "startDate": "2020-06-14T00:00:00",
     "endDate": "2020-12-31T23:59:59",
     "price": 35.50
   }
   ```

Las respuestas de error siguen una misma estructura para facilitar su interpretación.

## Ejecución de pruebas

El proyecto incluye pruebas unitarias e integrales para los escenarios solicitados. Para ejecutarlas se utiliza Maven:

```bash
./mvnw test
```

## Documentación

Se proporciona documentación OpenAPI accesible en `/swagger-ui.html` una vez iniciada la aplicación.

