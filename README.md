# Planta de Reciclaje - Backend API

## Tabla de Contenidos

1. [Introducción del Proyecto](#introducción-del-proyecto)
2. [Configuración del Entorno de Desarrollo](#configuración-del-entorno-de-desarrollo)
3. [Arquitectura del Proyecto](#arquitectura-del-proyecto)
4. [API REST](#api-rest)
5. [Generación de Reportes](#generación-de-reportes)
6. [Configuración de la Base de Datos](#configuración-de-la-base-de-datos)
7. [Validación de Entradas](#validación-de-entradas)
8. [Pruebas](#pruebas)
9. [Documentación OpenAPI/Swagger](#documentación-openapiswagger)

---

## Introducción del Proyecto

**Planta de Reciclaje** es un sistema backend desarrollado en **Spring Boot** que simula el registro y gestión de entregas de productos reciclables en una planta de reciclaje. El sistema permite realizar operaciones CRUD completas sobre las entregas, además de generar reportes diarios y semanales sobre las entregas realizadas.

### Propósito

El propósito principal del proyecto es:
- Gestionar las entregas de materiales reciclables (cartón, papel, plástico, metales, textiles, etc.)
- Calcular automáticamente la compensación económica basada en el tipo de material y la cantidad entregada
- Generar reportes de entregas en períodos específicos (diario o semanal)
- Proveer una API REST documentada y fácil de consumir

### Componentes Principales

1. **Entidades**: Administradores, Materiales y Entregas
2. **API REST**: Endpoints para operaciones CRUD y generación de reportes
3. **Persistencia**: Gestión de datos con JPA/Hibernate y MySQL
4. **Validación**: Validación automática de entradas con Bean Validation
5. **Documentación**: API documentada con OpenAPI/Swagger UI

---

## Configuración del Entorno de Desarrollo

### Requisitos Previos

- **Java 21** o superior
- **Maven 3.8+**
- **MySQL 8.0+**
- **IntelliJ IDEA 2025.2** (recomendado)
- **Postman** o similar para pruebas de API

### Dependencias del Proyecto (pom.xml)

El proyecto utiliza las siguientes dependencias principales:

```xml
<properties>
    <java.version>21</java.version>
    <org.mapstruct.version>1.6.3</org.mapstruct.version>
    <lombok.version>1.18.38</lombok.version>
</properties>

<dependencies>
    <!-- Spring Boot Starter Data JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <!-- Spring Boot Starter Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- Spring Boot Starter Validation -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    
    <!-- MySQL Connector -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>
    
    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>${lombok.version}</version>
        <scope>provided</scope>
    </dependency>
    
    <!-- MapStruct -->
    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct</artifactId>
        <version>${org.mapstruct.version}</version>
    </dependency>
    
    <!-- SpringDoc OpenAPI -->
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.8.12</version>
    </dependency>
    
    <!-- Spring Boot Starter Test -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

#### Descripción de Dependencias Clave

- **Spring Boot Starter Data JPA**: Proporciona soporte para persistencia de datos usando JPA/Hibernate
- **Spring Boot Starter Web**: Permite crear aplicaciones web y API REST
- **Spring Boot Starter Validation**: Habilita validación declarativa de beans usando anotaciones
- **MySQL Connector/J**: Driver JDBC para conectar con bases de datos MySQL
- **Lombok**: Reduce código repetitivo (getters, setters, constructores) mediante anotaciones
- **MapStruct**: Framework de mapeo de objetos DTO ↔ Entidades de forma automática
- **SpringDoc OpenAPI**: Genera documentación automática de la API REST con interfaz Swagger UI

### Configuración en IntelliJ IDEA

1. **Abrir el proyecto**:
   - `File > Open` y seleccionar la carpeta del proyecto
   - Esperar a que Maven descargue todas las dependencias

2. **Configurar el JDK**:
   - `File > Project Structure > Project`
   - Establecer **SDK**: Java 21
   - Establecer **Language Level**: 21

3. **Habilitar el procesamiento de anotaciones** (para Lombok y MapStruct):
   - `File > Settings > Build, Execution, Deployment > Compiler > Annotation Processors`
   - Marcar: ✓ **Enable annotation processing**

4. **Instalar plugins** (si no están instalados):
   - Lombok Plugin
   - Maven Helper

### Configuración de la Base de Datos MySQL

#### 1. Crear usuario de base de datos (opcional)

```sql
CREATE USER 'quintom'@'localhost' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON PlantaReciclajeDB.* TO 'quintom'@'localhost';
FLUSH PRIVILEGES;
```

#### 2. El esquema de la base de datos se crea automáticamente

Gracias a la configuración de Hibernate (`spring.jpa.hibernate.ddl-auto=create`), el esquema se genera automáticamente al iniciar la aplicación.

---

## Arquitectura del Proyecto

### Estructura de Directorios

```
src/main/java/com/oxo/planta_de_reciclaje/
├── PlantaDeReciclajeApplication.java    # Clase principal de Spring Boot
├── dominio/                              # Capa de dominio
│   ├── dto/                              # Data Transfer Objects
│   │   ├── EntregaDto.java
│   │   ├── ModEntregaDto.java
│   │   ├── MaterialesDto.java
│   │   ├── ModMaterialesDto.java
│   │   └── AdministradoresDto.java
│   ├── exception/                        # Excepciones personalizadas
│   │   ├── EntregaAlreadyExistsException.java
│   │   ├── EntregaNotExistsException.java
│   │   ├── MaterialExistsException.java
│   │   ├── MaterialNotExistsException.java
│   │   ├── AdministradorExistsException.java
│   │   └── AdministradorNotExistsException.java
│   └── service/                          # Lógica de negocio
│       ├── EntregaService.java
│       ├── MaterialesService.java
│       └── AdministradoresService.java
├── persistence/                          # Capa de persistencia
│   ├── entity/                           # Entidades JPA
│   │   ├── Entrega.java
│   │   ├── Materiales.java
│   │   └── Administradores.java
│   ├── crud/                             # Repositorios CRUD de Spring Data
│   │   ├── CrudEntregaEntity.java
│   │   ├── CrudMaterialesEntity.java
│   │   └── CrudAdministradoresEntity.java
│   ├── mapper/                           # Mappers de MapStruct
│   │   ├── EntregaMapper.java
│   │   ├── MaterialesMapper.java
│   │   └── AdministradoresMapper.java
│   ├── EntregaEntityRepository.java
│   ├── MaterialesEntityRepository.java
│   └── AdministradoresEntityRepository.java
├── repository/                           # Interfaces de repositorio
│   ├── EntregaRepository.java
│   ├── MaterialesRepository.java
│   └── AdministradoresRepository.java
└── web/                                  # Capa de presentación
    └── controller/                       # Controladores REST
        ├── EntregaController.java
        ├── MaterialesController.java
        └── AdministradoresController.java
```

### Capas del Proyecto

#### 1. Capa de Presentación (Web Layer)

**Responsabilidad**: Manejar las peticiones HTTP, validar entradas y retornar respuestas.

**Componentes**:
- **Controladores** (`@RestController`): Exponen endpoints REST
- Utilizan anotaciones de Spring MVC: `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`
- Documentados con anotaciones OpenAPI: `@Operation`, `@ApiResponse`, `@Parameter`

**Ejemplo**:
```java
@RestController
@RequestMapping("/entregas")
public class EntregaController {
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener una entrega por ID")
    public ResponseEntity<EntregaDto> obtenerPorId(@PathVariable Integer id) {
        // Lógica del controlador
    }
}
```

#### 2. Capa de Servicio (Service Layer)

**Responsabilidad**: Contiene la lógica de negocio de la aplicación.

**Componentes**:
- Clases anotadas con `@Service`
- Orquestan operaciones entre repositorios
- Calculan compensaciones, generan reportes, etc.

**Ejemplo**:
```java
@Service
public class EntregaService {
    
    public EntregaDto guardarEntrega(EntregaDto dto) {
        // Cálculo de compensación
        MaterialesDto material = materialesRepository.buscarPorId(dto.idMaterial());
        BigDecimal compensacion = material.precioPorKg().multiply(dto.cantidadKg());
        // Guardar en base de datos
    }
}
```

#### 3. Capa de Repositorio (Repository Layer)

**Responsabilidad**: Abstraer el acceso a datos y operaciones de persistencia.

**Componentes**:
- **Interfaces de repositorio**: Define contratos para acceso a datos
- **Implementaciones**: Realizan operaciones CRUD usando Spring Data JPA
- **Repositorios CRUD**: Extienden `CrudRepository` de Spring Data

**Ejemplo**:
```java
public interface CrudEntregaEntity extends CrudRepository<Entrega, Integer> {
    List<Entrega> findByFechaEntregaBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
```

#### 4. Capa de Persistencia (Persistence Layer)

**Responsabilidad**: Mapeo objeto-relacional (ORM).

**Componentes**:
- **Entidades JPA** (`@Entity`): Representan tablas de la base de datos
- **Mappers** (`@Mapper`): Convierten entre DTOs y Entidades usando MapStruct

**Ejemplo de Entidad**:
```java
@Entity
@Table(name = "Entregas")
@Data
public class Entrega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEntrega;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidadKg;
    
    @CreationTimestamp
    private LocalDate fechaEntrega;
    
    // Más campos...
}
```

### Uso de DTOs (Data Transfer Objects)

Los DTOs se utilizan para transferir datos entre las capas de la aplicación y para exponer/recibir datos en la API REST.

**Ventajas**:
- Desacoplamiento entre la capa de presentación y la capa de persistencia
- Control sobre qué datos se exponen en la API
- Validación de entradas con anotaciones Bean Validation
- Reducción de transferencia de datos innecesarios

**Ejemplo de DTO**:
```java
public record EntregaDto(
    Integer idEntrega,
    @NotNull(message = "Ingresar la cantidad es obligatorio")
    @Positive(message = "La cantidad ingresada debe ser positiva")
    BigDecimal cantidadKg,
    LocalDate fechaEntrega,
    String nombreProveedor,
    BigDecimal compensacion,
    Integer idMaterial
) {}
```

---

## API REST

### URL Base

```
http://localhost:8080/plantaReciclaje/api
```

Todas las rutas están prefijadas con `/plantaReciclaje/api` según la configuración en `application.properties`:
```properties
server.servlet.context-path=/plantaReciclaje/api
```

### Endpoints de Entregas

#### 1. Obtener Todas las Entregas

**Endpoint**: `GET /entregas`

**Descripción**: Retorna una lista de todas las entregas registradas en el sistema.

**Respuesta exitosa**: `200 OK`

**Ejemplo con curl**:
```bash
curl -X GET http://localhost:8080/plantaReciclaje/api/entregas
```

**Ejemplo de respuesta**:
```json
[
  {
    "idEntrega": 1,
    "cantidadKg": 45.50,
    "fechaEntrega": "2025-10-08",
    "nombreProveedor": "Recicladora El Progreso",
    "compensacion": 5.46,
    "idMaterial": 1
  },
  {
    "idEntrega": 2,
    "cantidadKg": 30.00,
    "fechaEntrega": "2025-10-08",
    "nombreProveedor": "Papeles del Norte",
    "compensacion": 8.10,
    "idMaterial": 2
  }
]
```

---

#### 2. Obtener una Entrega por ID

**Endpoint**: `GET /entregas/{id}`

**Descripción**: Busca y retorna una entrega específica por su identificador único.

**Parámetros**:
- `id` (path parameter): ID de la entrega a buscar

**Respuestas**:
- `200 OK`: Entrega encontrada
- `404 Not Found`: Entrega no encontrada

**Ejemplo con curl**:
```bash
curl -X GET http://localhost:8080/plantaReciclaje/api/entregas/1
```

**Ejemplo de respuesta exitosa**:
```json
{
  "idEntrega": 1,
  "cantidadKg": 45.50,
  "fechaEntrega": "2025-10-08",
  "nombreProveedor": "Recicladora El Progreso",
  "compensacion": 5.46,
  "idMaterial": 1
}
```

---

#### 3. Crear una Nueva Entrega

**Endpoint**: `POST /entregas`

**Descripción**: Crea y guarda una nueva entrega en la base de datos. La compensación se calcula automáticamente basándose en el tipo de material y la cantidad entregada.

**Respuestas**:
- `201 Created`: Entrega creada exitosamente
- `400 Bad Request`: Datos de entrada inválidos

**Body (JSON)**:
```json
{
  "cantidadKg": 25.50,
  "nombreProveedor": "Reciclajes Unidos",
  "idMaterial": 3
}
```

**Ejemplo con curl**:
```bash
curl -X POST http://localhost:8080/plantaReciclaje/api/entregas \
  -H "Content-Type: application/json" \
  -d '{
    "cantidadKg": 25.50,
    "nombreProveedor": "Reciclajes Unidos",
    "idMaterial": 3
  }'
```

**Ejemplo de respuesta exitosa** (`201 Created`):
```json
{
  "idEntrega": 21,
  "cantidadKg": 25.50,
  "fechaEntrega": "2025-10-08",
  "nombreProveedor": "Reciclajes Unidos",
  "compensacion": 15.56,
  "idMaterial": 3
}
```

**Notas importantes**:
- El campo `compensacion` se calcula automáticamente: `precioPorKg * cantidadKg`
- El campo `fechaEntrega` se establece automáticamente al momento de la creación
- El campo `idEntrega` es generado automáticamente por la base de datos

---

#### 4. Modificar una Entrega Existente

**Endpoint**: `PUT /entregas/{id}`

**Descripción**: Actualiza la información de una entrega existente. Actualmente solo permite modificar el nombre del proveedor.

**Parámetros**:
- `id` (path parameter): ID de la entrega a modificar

**Respuestas**:
- `200 OK`: Entrega actualizada exitosamente
- `404 Not Found`: Entrega no encontrada
- `400 Bad Request`: Datos de entrada inválidos

**Body (JSON)**:
```json
{
  "nombreProveedor": "Nuevo Nombre del Proveedor"
}
```

**Ejemplo con curl**:
```bash
curl -X PUT http://localhost:8080/plantaReciclaje/api/entregas/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nombreProveedor": "Recicladora El Progreso - Actualizado"
  }'
```

**Ejemplo de respuesta exitosa** (`200 OK`):
```json
{
  "idEntrega": 1,
  "cantidadKg": 45.50,
  "fechaEntrega": "2025-10-08",
  "nombreProveedor": "Recicladora El Progreso - Actualizado",
  "compensacion": 5.46,
  "idMaterial": 1
}
```

---

#### 5. Eliminar una Entrega

**Endpoint**: `DELETE /entregas/{id}`

**Descripción**: Elimina una entrega existente por su ID.

**Parámetros**:
- `id` (path parameter): ID de la entrega a eliminar

**Respuestas**:
- `204 No Content`: Entrega eliminada exitosamente
- `404 Not Found`: Entrega no encontrada

**Ejemplo con curl**:
```bash
curl -X DELETE http://localhost:8080/plantaReciclaje/api/entregas/1
```

**Respuesta exitosa**: `204 No Content` (sin cuerpo de respuesta)

---

#### 6. Generar Reporte de Entregas

**Endpoint**: `GET /entregas/reporte`

**Descripción**: Genera un reporte diario o semanal de entregas basado en una fecha específica.

**Parámetros** (query parameters):
- `fecha` (obligatorio): Fecha para el reporte en formato `YYYY-MM-DD`
- `tipo` (obligatorio): Tipo de reporte, valores válidos: `diario` o `semanal`

**Respuestas**:
- `200 OK`: Reporte generado exitosamente
- `400 Bad Request`: Parámetros inválidos (tipo inválido o formato de fecha incorrecto)

**Ejemplo de reporte diario con curl**:
```bash
curl -X GET "http://localhost:8080/plantaReciclaje/api/entregas/reporte?fecha=2025-10-08&tipo=diario"
```

**Ejemplo de reporte semanal con curl**:
```bash
curl -X GET "http://localhost:8080/plantaReciclaje/api/entregas/reporte?fecha=2025-10-08&tipo=semanal"
```

**Ejemplo de respuesta para reporte diario**:
```json
[
  {
    "idEntrega": 1,
    "cantidadKg": 45.50,
    "fechaEntrega": "2025-10-08",
    "nombreProveedor": "Recicladora El Progreso",
    "compensacion": 5.46,
    "idMaterial": 1
  },
  {
    "idEntrega": 2,
    "cantidadKg": 30.00,
    "fechaEntrega": "2025-10-08",
    "nombreProveedor": "Papeles del Norte",
    "compensacion": 8.10,
    "idMaterial": 2
  }
]
```

---

### Ejemplos con Postman

#### Configuración Inicial en Postman

1. **Crear una nueva colección**: `Planta de Reciclaje API`
2. **Establecer variable de colección**:
   - Variable: `baseUrl`
   - Valor: `http://localhost:8080/plantaReciclaje/api`

#### Ejemplo 1: Crear una nueva entrega

1. **Método**: POST
2. **URL**: `{{baseUrl}}/entregas`
3. **Headers**:
   - `Content-Type`: `application/json`
4. **Body** (raw JSON):
```json
{
  "cantidadKg": 100.00,
  "nombreProveedor": "Metales del Sur",
  "idMaterial": 5
}
```
5. **Resultado esperado**: `201 Created` con la entrega creada y compensación calculada

#### Ejemplo 2: Generar reporte semanal

1. **Método**: GET
2. **URL**: `{{baseUrl}}/entregas/reporte`
3. **Params**:
   - `fecha`: `2025-10-08`
   - `tipo`: `semanal`
4. **Resultado esperado**: `200 OK` con lista de entregas de la semana

---

## Generación de Reportes

La funcionalidad de generación de reportes permite obtener entregas filtradas por períodos de tiempo específicos.

### Tipos de Reportes

#### 1. Reporte Diario

Genera un reporte con todas las entregas realizadas en un día específico.

**Parámetros**:
- `fecha`: Fecha específica en formato `YYYY-MM-DD`
- `tipo`: `diario`

**Lógica de implementación**:
```java
public List<EntregaDto> generarReporteDiario(LocalDate fecha) {
    // Buscar entregas donde fechaEntrega = fecha
    List<Entrega> entregas = crudRepository.findByFechaEntregaBetween(fecha, fecha);
    return mapper.toDto(entregas);
}
```

**Ejemplo de uso**:
```bash
GET /entregas/reporte?fecha=2025-10-08&tipo=diario
```

**Caso de uso**: Obtener todas las entregas realizadas el 8 de octubre de 2025.

---

#### 2. Reporte Semanal

Genera un reporte con todas las entregas realizadas durante una semana completa (de lunes a domingo).

**Parámetros**:
- `fecha`: Cualquier fecha dentro de la semana deseada (formato `YYYY-MM-DD`)
- `tipo`: `semanal`

**Lógica de implementación**:
```java
public List<EntregaDto> generarReporteSemanal(LocalDate fecha) {
    // Calcular inicio de semana (lunes)
    LocalDate inicioSemana = fecha.with(DayOfWeek.MONDAY);
    // Calcular fin de semana (domingo)
    LocalDate finSemana = fecha.with(DayOfWeek.SUNDAY);
    
    // Buscar todas las entregas entre lunes y domingo
    List<Entrega> entregas = crudRepository.findByFechaEntregaBetween(inicioSemana, finSemana);
    return mapper.toDto(entregas);
}
```

**Ejemplo de uso**:
```bash
GET /entregas/reporte?fecha=2025-10-08&tipo=semanal
```

**Caso de uso**: Si la fecha proporcionada es `2025-10-08` (un miércoles), el reporte incluirá todas las entregas desde el lunes 6 de octubre hasta el domingo 12 de octubre de 2025.

---

### Consideraciones Importantes

1. **Formato de fecha**: Debe ser `YYYY-MM-DD` (ISO 8601)
2. **Validación del tipo**: Solo se aceptan los valores `diario` o `semanal` (case-insensitive)
3. **Respuesta vacía**: Si no hay entregas en el período especificado, se retorna una lista vacía `[]`
4. **Zona horaria**: Las fechas se manejan como `LocalDate` sin considerar zona horaria

### Ejemplos Completos de Reportes

#### Ejemplo 1: Reporte diario con resultados

**Request**:
```bash
curl -X GET "http://localhost:8080/plantaReciclaje/api/entregas/reporte?fecha=2025-10-08&tipo=diario"
```

**Response** (`200 OK`):
```json
[
  {
    "idEntrega": 1,
    "cantidadKg": 45.50,
    "fechaEntrega": "2025-10-08",
    "nombreProveedor": "Recicladora El Progreso",
    "compensacion": 5.46,
    "idMaterial": 1
  },
  {
    "idEntrega": 2,
    "cantidadKg": 30.00,
    "fechaEntrega": "2025-10-08",
    "nombreProveedor": "Papeles del Norte",
    "compensacion": 8.10,
    "idMaterial": 2
  }
]
```

#### Ejemplo 2: Reporte semanal

**Request**:
```bash
curl -X GET "http://localhost:8080/plantaReciclaje/api/entregas/reporte?fecha=2025-10-08&tipo=semanal"
```

**Response** (`200 OK`): Lista de todas las entregas desde el lunes 6 de octubre hasta el domingo 12 de octubre.

#### Ejemplo 3: Tipo de reporte inválido

**Request**:
```bash
curl -X GET "http://localhost:8080/plantaReciclaje/api/entregas/reporte?fecha=2025-10-08&tipo=mensual"
```

**Response** (`400 Bad Request`): Sin cuerpo de respuesta, ya que `mensual` no es un tipo válido.

---

## Configuración de la Base de Datos

### Archivo application.properties

Ubicación: `src/main/resources/application.properties`

```properties
# Nombre de la aplicación
spring.application.name=planta-de-reciclaje

# Configuración del servidor
server.servlet.context-path=/plantaReciclaje/api

# Configuración de la base de datos MySQL
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/PlantaReciclajeDB?createDatabaseIfNotExist=true
spring.datasource.username=quintom
spring.datasource.password=admin

# Configuración de JPA y Hibernate
spring.jpa.defer-datasource-initialization=true
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Configuración para inicializar la base de datos con scripts SQL
spring.sql.init.mode=always
```

### Descripción de las Propiedades

#### Configuración del Servidor

- **`server.servlet.context-path`**: Define el prefijo de ruta para todas las URLs de la API
  - Valor: `/plantaReciclaje/api`
  - Efecto: Todas las rutas empiezan con `http://localhost:8080/plantaReciclaje/api`

#### Configuración de la Fuente de Datos (DataSource)

- **`spring.datasource.driver-class-name`**: Driver JDBC para MySQL
  - Valor: `com.mysql.cj.jdbc.Driver`
  
- **`spring.datasource.url`**: URL de conexión a la base de datos
  - Valor: `jdbc:mysql://127.0.0.1:3306/PlantaReciclajeDB?createDatabaseIfNotExist=true`
  - Host: `127.0.0.1` (localhost)
  - Puerto: `3306` (puerto por defecto de MySQL)
  - Base de datos: `PlantaReciclajeDB`
  - Parámetro `createDatabaseIfNotExist=true`: Crea la base de datos si no existe

- **`spring.datasource.username`**: Usuario de MySQL
  - Valor: `quintom`
  
- **`spring.datasource.password`**: Contraseña de MySQL
  - Valor: `admin`

#### Configuración de JPA/Hibernate

- **`spring.jpa.defer-datasource-initialization`**: Retrasa la inicialización del datasource hasta después de que Hibernate cree el esquema
  - Valor: `true`
  - Necesario para que `data.sql` se ejecute después de crear las tablas

- **`spring.jpa.hibernate.ddl-auto`**: Estrategia de generación del esquema de base de datos
  - Valor: `create`
  - Efecto: **Elimina y recrea** todas las tablas cada vez que se inicia la aplicación
  - **⚠️ ADVERTENCIA**: En producción, usar `validate` o `update` para no perder datos

- **`spring.jpa.show-sql`**: Muestra las consultas SQL en la consola
  - Valor: `true`
  - Útil para depuración y desarrollo

- **`spring.jpa.properties.hibernate.format_sql`**: Formatea las consultas SQL para mejor legibilidad
  - Valor: `true`

- **`spring.jpa.properties.hibernate.dialect`**: Dialecto SQL específico de MySQL
  - Valor: `org.hibernate.dialect.MySQLDialect`
  - Optimiza las consultas SQL para MySQL

#### Configuración de Inicialización SQL

- **`spring.sql.init.mode`**: Controla la ejecución de scripts SQL de inicialización
  - Valor: `always`
  - Efecto: Ejecuta `data.sql` cada vez que se inicia la aplicación
  - Otros valores: `never` (no ejecutar), `embedded` (solo para bases de datos embebidas)

---

### Scripts de Inicialización

#### Archivo data.sql

Ubicación: `src/main/resources/data.sql`

Este archivo contiene datos de prueba que se insertan automáticamente al iniciar la aplicación:

1. **Materiales**: 20 tipos de materiales reciclables con sus precios por kg
   - Ejemplos: cartón ($0.12/kg), papel blanco ($0.27/kg), cobre ($8.00/kg), etc.

2. **Entregas**: 20 entregas de ejemplo con diferentes materiales y proveedores

3. **Administradores**: 10 administradores de ejemplo

**Ejecución**: El script se ejecuta automáticamente gracias a `spring.sql.init.mode=always`

---

### Esquema de Base de Datos

#### Tabla: Materiales

```sql
CREATE TABLE Materiales (
    idMaterial INT PRIMARY KEY AUTO_INCREMENT,
    tipoMaterial VARCHAR(50) NOT NULL,
    precioPorKg DECIMAL(10,2) NOT NULL
);
```

**Campos**:
- `idMaterial`: Identificador único (clave primaria, auto-incremental)
- `tipoMaterial`: Nombre del tipo de material (ej: "carton", "papelBlanco")
- `precioPorKg`: Precio por kilogramo del material

#### Tabla: Entregas

```sql
CREATE TABLE Entregas (
    idEntrega INT PRIMARY KEY AUTO_INCREMENT,
    idMaterial INT NOT NULL,
    cantidadKg DECIMAL(10,2) NOT NULL,
    fechaEntrega DATE NOT NULL,
    nombreProveedor VARCHAR(100) NOT NULL,
    compensacion DECIMAL(10,2) NOT NULL,
    CONSTRAINT FK_Entregas_Materiales 
        FOREIGN KEY (idMaterial) REFERENCES Materiales(idMaterial)
);
```

**Campos**:
- `idEntrega`: Identificador único (clave primaria, auto-incremental)
- `idMaterial`: Referencia al material entregado (clave foránea)
- `cantidadKg`: Cantidad de material entregado en kilogramos
- `fechaEntrega`: Fecha en que se realizó la entrega
- `nombreProveedor`: Nombre del proveedor que realizó la entrega
- `compensacion`: Compensación económica calculada

#### Tabla: Administradores

```sql
CREATE TABLE Administradores (
    idAdministrador INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    correoElectronico VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(100) NOT NULL
);
```

**Campos**:
- `idAdministrador`: Identificador único (clave primaria, auto-incremental)
- `nombre`: Nombre completo del administrador
- `correoElectronico`: Email del administrador (único)
- `contrasena`: Contraseña del administrador (en producción debería estar hasheada)

---

## Validación de Entradas

El proyecto utiliza **Bean Validation** (Jakarta Validation) para validar automáticamente los datos de entrada en las solicitudes HTTP.

### Anotaciones de Validación

#### EntregaDto

```java
public record EntregaDto(
    Integer idEntrega,
    
    @NotNull(message = "Ingresar la cantidad es obligatorio")
    @Positive(message = "La cantidad ingresada debe ser positiva")
    BigDecimal cantidadKg,
    
    LocalDate fechaEntrega,
    String nombreProveedor,
    BigDecimal compensacion,
    Integer idMaterial
) {}
```

**Validaciones aplicadas**:
- **`@NotNull`**: El campo `cantidadKg` no puede ser nulo
- **`@Positive`**: El campo `cantidadKg` debe ser un número positivo (> 0)

#### ModEntregaDto

```java
public record ModEntregaDto(
    @NotBlank(message = "El nombre en este campo es obligatorio")
    String nombreProveedor
) {}
```

**Validaciones aplicadas**:
- **`@NotBlank`**: El campo `nombreProveedor` no puede ser nulo, vacío o contener solo espacios

---

### Cómo Funciona la Validación

1. **Activación**: Se activa usando `@Valid` en el parámetro del controlador
   ```java
   @PostMapping
   public ResponseEntity<EntregaDto> crear(@Valid @RequestBody EntregaDto dto) {
       // Si la validación falla, Spring lanza una excepción automáticamente
   }
   ```

2. **Respuesta en caso de error**: Spring Boot retorna automáticamente `400 Bad Request` con detalles del error
   ```json
   {
     "timestamp": "2025-10-08T16:30:00.000+00:00",
     "status": 400,
     "error": "Bad Request",
     "message": "Ingresar la cantidad es obligatorio",
     "path": "/plantaReciclaje/api/entregas"
   }
   ```

---

### Ejemplos de Validación

#### Ejemplo 1: Crear entrega sin cantidadKg

**Request**:
```bash
curl -X POST http://localhost:8080/plantaReciclaje/api/entregas \
  -H "Content-Type: application/json" \
  -d '{
    "nombreProveedor": "Reciclajes Unidos",
    "idMaterial": 3
  }'
```

**Response** (`400 Bad Request`):
```json
{
  "timestamp": "2025-10-08T16:30:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Ingresar la cantidad es obligatorio"
}
```

#### Ejemplo 2: Crear entrega con cantidad negativa

**Request**:
```bash
curl -X POST http://localhost:8080/plantaReciclaje/api/entregas \
  -H "Content-Type: application/json" \
  -d '{
    "cantidadKg": -10.50,
    "nombreProveedor": "Reciclajes Unidos",
    "idMaterial": 3
  }'
```

**Response** (`400 Bad Request`):
```json
{
  "timestamp": "2025-10-08T16:30:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "La cantidad ingresada debe ser positiva"
}
```

#### Ejemplo 3: Modificar entrega con nombre vacío

**Request**:
```bash
curl -X PUT http://localhost:8080/plantaReciclaje/api/entregas/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nombreProveedor": ""
  }'
```

**Response** (`400 Bad Request`):
```json
{
  "timestamp": "2025-10-08T16:30:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "El nombre en este campo es obligatorio"
}
```

---

### Anotaciones Comunes de Validación

| Anotación | Descripción | Ejemplo |
|-----------|-------------|---------|
| `@NotNull` | El campo no puede ser nulo | `@NotNull String campo` |
| `@NotEmpty` | El campo no puede estar vacío (para colecciones y strings) | `@NotEmpty List<String> lista` |
| `@NotBlank` | El campo no puede ser nulo, vacío o solo espacios | `@NotBlank String nombre` |
| `@Positive` | El número debe ser mayor a 0 | `@Positive BigDecimal cantidad` |
| `@PositiveOrZero` | El número debe ser >= 0 | `@PositiveOrZero Integer stock` |
| `@Min(value)` | El número debe ser >= value | `@Min(1) Integer edad` |
| `@Max(value)` | El número debe ser <= value | `@Max(100) Integer porcentaje` |
| `@Size(min, max)` | Tamaño de string o colección | `@Size(min=3, max=50) String nombre` |
| `@Email` | Valida formato de email | `@Email String correo` |
| `@Pattern(regexp)` | Valida contra expresión regular | `@Pattern(regexp="\\d{4}") String codigo` |

---

## Pruebas

### Estructura de Pruebas

El proyecto incluye la infraestructura básica para pruebas con Spring Boot Test.

**Ubicación**: `src/test/java/com/oxo/planta_de_reciclaje/`

#### Clase de Prueba Principal

```java
@SpringBootTest
class PlantaDeReciclajeApplicationTests {
    @Test
    void contextLoads() {
        // Verifica que el contexto de Spring se carga correctamente
    }
}
```

### Ejecutar Pruebas

#### Con Maven

```bash
./mvnw test
```

#### Con IntelliJ IDEA

1. Clic derecho en la clase de prueba
2. Seleccionar `Run 'PlantaDeReciclajeApplicationTests'`

---

### Pruebas de Integración Sugeridas

Aunque el proyecto no incluye pruebas exhaustivas, se recomienda implementar:

#### 1. Pruebas de Controladores

```java
@SpringBootTest
@AutoConfigureMockMvc
class EntregaControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void deberiaCrearEntrega() throws Exception {
        mockMvc.perform(post("/entregas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cantidadKg\": 10.5, \"nombreProveedor\": \"Test\", \"idMaterial\": 1}"))
                .andExpect(status().isCreated());
    }
}
```

#### 2. Pruebas de Servicios

```java
@SpringBootTest
class EntregaServiceTest {
    
    @Autowired
    private EntregaService entregaService;
    
    @Test
    void deberiaCalcularCompensacionCorrectamente() {
        EntregaDto dto = new EntregaDto(null, new BigDecimal("10.00"), null, "Test", null, 1);
        EntregaDto resultado = entregaService.guardarEntrega(dto);
        
        assertNotNull(resultado.compensacion());
        assertTrue(resultado.compensacion().compareTo(BigDecimal.ZERO) > 0);
    }
}
```

#### 3. Pruebas de Repositorio

```java
@DataJpaTest
class CrudEntregaEntityTest {
    
    @Autowired
    private CrudEntregaEntity repository;
    
    @Test
    void deberiaEncontrarEntregasPorFecha() {
        LocalDate fecha = LocalDate.now();
        List<Entrega> entregas = repository.findByFechaEntregaBetween(fecha, fecha);
        
        assertNotNull(entregas);
    }
}
```

---

## Documentación OpenAPI/Swagger

El proyecto utiliza **SpringDoc OpenAPI** para generar documentación interactiva de la API.

### Acceder a la Documentación

Una vez que la aplicación está en ejecución:

#### Swagger UI (Interfaz Interactiva)

```
http://localhost:8080/plantaReciclaje/api/swagger-ui/index.html
```

**Características**:
- Interfaz visual interactiva
- Permite probar endpoints directamente desde el navegador
- Muestra esquemas de datos, parámetros y respuestas
- No requiere herramientas adicionales como Postman

#### Especificación OpenAPI (JSON)

```
http://localhost:8080/plantaReciclaje/api/v3/api-docs
```

Este endpoint retorna la especificación OpenAPI completa en formato JSON.

---

### Anotaciones OpenAPI Utilizadas

El proyecto utiliza anotaciones para documentar los endpoints:

```java
@Tag(name = "Entregas", description = "Operaciones CRUD sobre las entregas")
public class EntregaController {
    
    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener una entrega por ID",
        description = "Busca y retorna una entrega específica por su identificador único."
    )
    @ApiResponse(responseCode = "200", description = "Entrega encontrada y devuelta.")
    @ApiResponse(responseCode = "404", description = "Entrega no encontrada.", content = @Content)
    public ResponseEntity<EntregaDto> obtenerPorId(
        @Parameter(description = "ID de la entrega a buscar.", example = "1")
        @PathVariable Integer id
    ) {
        // Implementación
    }
}
```

**Anotaciones**:
- **`@Tag`**: Agrupa endpoints relacionados
- **`@Operation`**: Documenta un endpoint específico
- **`@ApiResponse`**: Define respuestas posibles del endpoint
- **`@Parameter`**: Documenta parámetros de entrada

---

### Uso de Swagger UI

#### 1. Explorar Endpoints

- Navegar por los diferentes grupos de endpoints (Entregas, Materiales, Administradores)
- Ver detalles de cada operación: parámetros, body, respuestas

#### 2. Probar un Endpoint

1. Hacer clic en el endpoint deseado (ej: `POST /entregas`)
2. Clic en el botón **"Try it out"**
3. Completar los parámetros/body requeridos
4. Clic en **"Execute"**
5. Ver la respuesta en tiempo real

#### 3. Ver Modelos de Datos

Desplazarse hacia abajo para ver los **Schemas** (modelos de datos):
- EntregaDto
- ModEntregaDto
- MaterialesDto
- etc.

---

## Instalación y Ejecución

### Paso 1: Clonar el Repositorio

```bash
git clone <url-del-repositorio>
cd ProyectoFinal_PlantaDeReciclaje
```

### Paso 2: Configurar MySQL

1. Instalar MySQL 8.0+
2. Iniciar el servidor MySQL
3. (Opcional) Crear usuario según `application.properties`:
   ```sql
   CREATE USER 'quintom'@'localhost' IDENTIFIED BY 'admin';
   GRANT ALL PRIVILEGES ON *.* TO 'quintom'@'localhost';
   FLUSH PRIVILEGES;
   ```

### Paso 3: Compilar el Proyecto

```bash
./mvnw clean install
```

### Paso 4: Ejecutar la Aplicación

```bash
./mvnw spring-boot:run
```

O desde IntelliJ IDEA:
- Abrir `PlantaDeReciclajeApplication.java`
- Clic derecho > `Run 'PlantaDeReciclajeApplication'`

### Paso 5: Verificar que la Aplicación Está Funcionando

```bash
curl http://localhost:8080/plantaReciclaje/api/entregas
```

Debería retornar una lista de entregas.

---

## Solución de Problemas Comunes

### Error: "Communications link failure"

**Causa**: MySQL no está en ejecución o la configuración de conexión es incorrecta.

**Solución**:
1. Verificar que MySQL esté en ejecución
2. Revisar las credenciales en `application.properties`
3. Verificar que el puerto 3306 esté disponible

### Error: "Access denied for user"

**Causa**: Credenciales de MySQL incorrectas.

**Solución**:
1. Verificar usuario y contraseña en `application.properties`
2. Asegurarse de que el usuario tenga permisos en la base de datos

### Error: "Table doesn't exist"

**Causa**: El esquema no se creó correctamente o `spring.jpa.hibernate.ddl-auto` no está configurado.

**Solución**:
1. Verificar que `spring.jpa.hibernate.ddl-auto=create` esté en `application.properties`
2. Reiniciar la aplicación para recrear las tablas

---

## Contribución

Para contribuir al proyecto:

1. Fork el repositorio
2. Crear una rama para tu feature: `git checkout -b feature/nueva-funcionalidad`
3. Commit tus cambios: `git commit -am 'Agregar nueva funcionalidad'`
4. Push a la rama: `git push origin feature/nueva-funcionalidad`
5. Crear un Pull Request

---

## Licencia

Este proyecto es de código abierto y está disponible bajo la licencia especificada en el archivo LICENSE.

---

## Contacto

Para preguntas o soporte, contactar al equipo de desarrollo del proyecto.

---

## Changelog

### Versión 0.0.1-SNAPSHOT
- Implementación inicial del sistema
- CRUD completo de entregas, materiales y administradores
- Generación de reportes diarios y semanales
- Documentación con OpenAPI/Swagger
- Validación de entradas con Bean Validation
