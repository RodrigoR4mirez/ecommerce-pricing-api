# ecommerce-pricing-api

Servicio REST en Spring Boot para consultar el precio de un producto según fecha de aplicación.

El precio de un producto se consulta mediante una petición GET a la ruta:

```
/brands/{brandId}/products/{productId}/prices?applicationDate=yyyy-MM-dd'T'HH:mm:ss
```

## Ejecución

```bash
./mvnw spring-boot:run
```

La base de datos en memoria se inicializa automáticamente con los datos de ejemplo.

El puerto donde se ejecuta la aplicación puede configurarse en `application.yml` mediante `server.port`.
La URL base del servicio se configura en la misma ubicación con la propiedad `api.base-url`.

Una vez iniciado el servicio puede acceder a la documentación interactiva de Swagger abriendo en el navegador la URL:

```
http://localhost:8080/swagger-ui.html
```
