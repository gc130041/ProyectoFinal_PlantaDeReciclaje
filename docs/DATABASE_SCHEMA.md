# Esquema de Base de Datos - Planta de Reciclaje

Este documento describe en detalle el esquema de la base de datos utilizado por el sistema de Planta de Reciclaje.

## Tabla de Contenidos

- [Diagrama ER](#diagrama-er)
- [Tablas](#tablas)
- [Relaciones](#relaciones)
- [Índices](#índices)
- [Scripts de Inicialización](#scripts-de-inicialización)
- [Datos de Ejemplo](#datos-de-ejemplo)

---

## Diagrama ER

```
┌─────────────────────┐
│   Administradores   │
├─────────────────────┤
│ idAdministrador (PK)│
│ nombre              │
│ correoElectronico   │
│ contrasena          │
└─────────────────────┘

┌─────────────────────┐         ┌─────────────────────┐
│     Materiales      │         │      Entregas       │
├─────────────────────┤         ├─────────────────────┤
│ idMaterial (PK)     │◄───────┤ idEntrega (PK)      │
│ tipoMaterial        │    1:N  │ cantidadKg          │
│ precioPorKg         │         │ fechaEntrega        │
└─────────────────────┘         │ nombreProveedor     │
                                │ compensacion        │
                                │ idMaterial (FK)     │
                                └─────────────────────┘
```

**Leyenda**:
- PK = Primary Key (Clave Primaria)
- FK = Foreign Key (Clave Foránea)
- 1:N = Relación uno a muchos

---

## Tablas

### Tabla: Materiales

**Descripción**: Almacena los diferentes tipos de materiales reciclables y sus precios por kilogramo.

#### Estructura

```sql
CREATE TABLE Materiales (
    idMaterial INT PRIMARY KEY AUTO_INCREMENT,
    tipoMaterial VARCHAR(50) NOT NULL,
    precioPorKg DECIMAL(10,2) NOT NULL,
    CONSTRAINT PK_Materiales PRIMARY KEY (idMaterial)
);
```

#### Campos

| Campo | Tipo | Nulo | Default | Descripción |
|-------|------|------|---------|-------------|
| idMaterial | INT | NO | AUTO_INCREMENT | Identificador único del material |
| tipoMaterial | VARCHAR(50) | NO | - | Nombre del tipo de material (ej: "carton", "papelBlanco") |
| precioPorKg | DECIMAL(10,2) | NO | - | Precio en dólares por kilogramo del material |

#### Constraints

- **Primary Key**: `idMaterial`
- **Not Null**: Todos los campos
- **Data Type**: `precioPorKg` almacena hasta 10 dígitos, 2 decimales (ej: 12345678.99)

#### Ejemplo de Datos

| idMaterial | tipoMaterial | precioPorKg |
|------------|--------------|-------------|
| 1 | carton | 0.12 |
| 2 | papelBlanco | 0.27 |
| 3 | petCristal | 0.61 |
| 4 | aluminioLatas | 1.23 |
| 5 | cobre | 8.00 |

#### Notas de Implementación JPA

```java
@Entity
@Table(name = "Materiales")
@Data
public class Materiales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMaterial;
    
    @Column(name = "tipo_material", nullable = false, length = 50)
    private String tipoMaterial;
    
    @Column(name = "precio_por_kg", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioPorKg;
}
```

---

### Tabla: Entregas

**Descripción**: Registra todas las entregas de materiales reciclables realizadas por los proveedores.

#### Estructura

```sql
CREATE TABLE Entregas (
    idEntrega INT PRIMARY KEY AUTO_INCREMENT,
    idMaterial INT NOT NULL,
    cantidadKg DECIMAL(10,2) NOT NULL,
    fechaEntrega DATE NOT NULL,
    nombreProveedor VARCHAR(100) NOT NULL,
    compensacion DECIMAL(10,2) NOT NULL,
    CONSTRAINT PK_Entregas PRIMARY KEY (idEntrega),
    CONSTRAINT FK_Entregas_Materiales 
        FOREIGN KEY (idMaterial) REFERENCES Materiales(idMaterial)
);
```

#### Campos

| Campo | Tipo | Nulo | Default | Descripción |
|-------|------|------|---------|-------------|
| idEntrega | INT | NO | AUTO_INCREMENT | Identificador único de la entrega |
| idMaterial | INT | NO | - | Referencia al material entregado (FK) |
| cantidadKg | DECIMAL(10,2) | NO | - | Cantidad de material en kilogramos |
| fechaEntrega | DATE | NO | - | Fecha en que se realizó la entrega |
| nombreProveedor | VARCHAR(100) | NO | - | Nombre del proveedor que realizó la entrega |
| compensacion | DECIMAL(10,2) | NO | - | Compensación económica calculada |

#### Constraints

- **Primary Key**: `idEntrega`
- **Foreign Key**: `idMaterial` → `Materiales(idMaterial)`
- **Not Null**: Todos los campos

#### Ejemplo de Datos

| idEntrega | cantidadKg | fechaEntrega | nombreProveedor | compensacion | idMaterial |
|-----------|------------|--------------|-----------------|--------------|------------|
| 1 | 45.50 | 2025-10-08 | Recicladora El Progreso | 5.46 | 1 |
| 2 | 30.00 | 2025-10-08 | Papeles del Norte | 8.10 | 2 |
| 3 | 15.75 | 2025-10-08 | Reciclajes San Juan | 9.61 | 3 |

#### Cálculo de Compensación

La compensación se calcula automáticamente en el servicio:
```
compensacion = cantidadKg × precioPorKg
```

**Ejemplo**:
- Material: Cartón (precioPorKg = $0.12)
- Cantidad: 45.50 kg
- Compensación: 45.50 × 0.12 = $5.46

#### Notas de Implementación JPA

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
    @Column(nullable = false, updatable = false)
    private LocalDate fechaEntrega;
    
    @Column(nullable = false, length = 64)
    private String nombreProveedor;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal compensacion;
    
    @ManyToOne
    @JoinColumn(name = "idMaterial", insertable = false, updatable = false)
    private Materiales material;
}
```

**Anotaciones importantes**:
- `@CreationTimestamp`: Establece automáticamente la fecha de creación
- `@ManyToOne`: Define la relación muchos-a-uno con Materiales
- `updatable = false`: La fecha de entrega no puede modificarse después de crearse

---

### Tabla: Administradores

**Descripción**: Almacena información de los administradores del sistema.

#### Estructura

```sql
CREATE TABLE Administradores (
    idAdministrador INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    correoElectronico VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(100) NOT NULL,
    CONSTRAINT PK_Administradores PRIMARY KEY (idAdministrador)
);
```

#### Campos

| Campo | Tipo | Nulo | Default | Descripción |
|-------|------|------|---------|-------------|
| idAdministrador | INT | NO | AUTO_INCREMENT | Identificador único del administrador |
| nombre | VARCHAR(100) | NO | - | Nombre completo del administrador |
| correoElectronico | VARCHAR(100) | NO | - | Correo electrónico (debe ser único) |
| contrasena | VARCHAR(100) | NO | - | Contraseña del administrador |

#### Constraints

- **Primary Key**: `idAdministrador`
- **Unique**: `correoElectronico`
- **Not Null**: Todos los campos

#### Ejemplo de Datos

| idAdministrador | nombre | correoElectronico | contrasena |
|----------------|---------|-------------------|------------|
| 1 | Juan Pérez | juan.perez@email.com | hash_contrasena_1 |
| 2 | Ana López | ana.lopez@email.com | hash_contrasena_2 |
| 3 | Carlos García | carlos.garcia@email.com | hash_contrasena_3 |

#### Consideraciones de Seguridad

⚠️ **IMPORTANTE**: En un ambiente de producción, las contraseñas deben:
- Estar hasheadas usando algoritmos seguros (bcrypt, Argon2, etc.)
- Nunca almacenarse en texto plano
- Tener políticas de complejidad mínima

#### Notas de Implementación JPA

```java
@Entity
@Table(name = "Administradores")
@Data
public class Administradores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAdministrador;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "correo_electronico", nullable = false, unique = true, length = 100)
    private String correoElectronico;
    
    @Column(nullable = false, length = 100)
    private String contrasena;
}
```

---

## Relaciones

### Entregas ↔ Materiales

**Tipo**: Muchos a Uno (Many-to-One)

**Descripción**: Cada entrega pertenece a un único material, pero un material puede tener múltiples entregas.

#### Relación en SQL

```sql
CONSTRAINT FK_Entregas_Materiales 
    FOREIGN KEY (idMaterial) REFERENCES Materiales(idMaterial)
```

#### Relación en JPA

```java
// En la entidad Entrega
@ManyToOne
@JoinColumn(name = "idMaterial", insertable = false, updatable = false)
private Materiales material;
```

#### Comportamiento

- **ON DELETE**: No especificado (comportamiento por defecto: RESTRICT)
  - No se puede eliminar un material si tiene entregas asociadas
- **ON UPDATE**: No especificado (comportamiento por defecto: RESTRICT)

#### Consultas Comunes

**Obtener todas las entregas de un material específico**:
```sql
SELECT e.* 
FROM Entregas e
WHERE e.idMaterial = 1;
```

**Obtener entregas con información del material**:
```sql
SELECT e.idEntrega, e.cantidadKg, e.fechaEntrega, 
       e.nombreProveedor, e.compensacion,
       m.tipoMaterial, m.precioPorKg
FROM Entregas e
INNER JOIN Materiales m ON e.idMaterial = m.idMaterial;
```

---

## Índices

### Índices Automáticos

Los siguientes índices se crean automáticamente:

1. **Primary Keys**:
   - `Materiales.idMaterial`
   - `Entregas.idEntrega`
   - `Administradores.idAdministrador`

2. **Unique Constraints**:
   - `Administradores.correoElectronico`

3. **Foreign Keys**:
   - `Entregas.idMaterial` (índice automático para optimizar JOINs)

### Índices Recomendados (Opcional)

Para optimizar consultas frecuentes:

```sql
-- Índice para búsquedas por fecha en reportes
CREATE INDEX idx_entregas_fecha 
ON Entregas(fechaEntrega);

-- Índice compuesto para reportes por fecha y material
CREATE INDEX idx_entregas_fecha_material 
ON Entregas(fechaEntrega, idMaterial);

-- Índice para búsquedas por nombre de proveedor
CREATE INDEX idx_entregas_proveedor 
ON Entregas(nombreProveedor);
```

---

## Scripts de Inicialización

### Orden de Ejecución

1. **Creación del esquema**: Hibernate con `ddl-auto=create`
2. **Inserción de datos**: `data.sql`

### Configuración en application.properties

```properties
# Hibernate crea el esquema
spring.jpa.hibernate.ddl-auto=create

# Retrasa la inicialización para que Hibernate cree tablas primero
spring.jpa.defer-datasource-initialization=true

# Ejecuta data.sql después de crear el esquema
spring.sql.init.mode=always
```

### Archivo data.sql

Ubicación: `src/main/resources/data.sql`

**Contenido**:
1. Inserción de 20 tipos de materiales
2. Inserción de 20 entregas de ejemplo
3. Inserción de 10 administradores

**Ejemplo de inserción de materiales**:
```sql
INSERT INTO Materiales (tipo_material, precio_por_kg)
VALUES ('carton', 0.12),
       ('papelBlanco', 0.27),
       ('petCristal', 0.61),
       ('aluminioLatas', 1.23),
       ('cobre', 8.00);
```

**Nota**: Los nombres de columnas en `data.sql` usan snake_case (ej: `tipo_material`) porque así es como Hibernate los genera por defecto.

---

## Datos de Ejemplo

### Materiales Completos

| ID | Material | Precio/kg | Categoría |
|----|----------|-----------|-----------|
| 1 | carton | $0.12 | Papel |
| 2 | papelBlanco | $0.27 | Papel |
| 3 | petCristal | $0.61 | Plástico |
| 4 | aluminioLatas | $1.23 | Metal |
| 5 | cobre | $8.00 | Metal |
| 6 | chatarraFierro | $0.16 | Metal |
| 7 | vidrio | $0.05 | Vidrio |
| 8 | polietilenoAltaDensidad | $0.63 | Plástico |
| 9 | polietilenoBajaDensidad | $0.29 | Plástico |
| 10 | polipropileno | $0.23 | Plástico |
| 11 | cementoGris | $0.15 | Construcción |
| 12 | arenaConstruccion | $0.04 | Construcción |
| 13 | gravaConstruccion | $0.04 | Construcción |
| 14 | yeso | $1.25 | Construcción |
| 15 | aceroInoxidable | $0.60 | Metal |
| 16 | bronce | $5.30 | Metal |
| 17 | telaAlgodon | $16.50 | Textil |
| 18 | telaPoliester | $10.40 | Textil |
| 19 | telaLana | $10.50 | Textil |
| 20 | seda | $30.00 | Textil |

### Estadísticas de Materiales

**Material más caro**: Seda ($30.00/kg)  
**Material más barato**: Arena y Grava de Construcción ($0.04/kg)  
**Precio promedio**: ~$3.50/kg

---

## Consultas Útiles

### Consultas de Reportes

#### Total de entregas por material
```sql
SELECT m.tipoMaterial, 
       COUNT(e.idEntrega) as totalEntregas,
       SUM(e.cantidadKg) as totalKg,
       SUM(e.compensacion) as totalCompensacion
FROM Materiales m
LEFT JOIN Entregas e ON m.idMaterial = e.idMaterial
GROUP BY m.idMaterial, m.tipoMaterial
ORDER BY totalCompensacion DESC;
```

#### Entregas del día
```sql
SELECT e.*, m.tipoMaterial
FROM Entregas e
INNER JOIN Materiales m ON e.idMaterial = m.idMaterial
WHERE e.fechaEntrega = CURDATE();
```

#### Top 5 proveedores por compensación
```sql
SELECT nombreProveedor,
       COUNT(*) as totalEntregas,
       SUM(cantidadKg) as totalKg,
       SUM(compensacion) as totalCompensacion
FROM Entregas
GROUP BY nombreProveedor
ORDER BY totalCompensacion DESC
LIMIT 5;
```

#### Promedio de compensación por material
```sql
SELECT m.tipoMaterial,
       AVG(e.compensacion) as compensacionPromedio,
       COUNT(e.idEntrega) as cantidadEntregas
FROM Materiales m
INNER JOIN Entregas e ON m.idMaterial = e.idMaterial
GROUP BY m.idMaterial, m.tipoMaterial
ORDER BY compensacionPromedio DESC;
```

---

## Consideraciones de Diseño

### Decisiones de Diseño

1. **Uso de INT para IDs**: 
   - Rango: -2,147,483,648 a 2,147,483,647
   - Suficiente para la mayoría de casos de uso
   - Si se esperan más de 2 mil millones de registros, considerar BIGINT

2. **DECIMAL(10,2) para valores monetarios**:
   - Precisión exacta para cálculos financieros
   - Evita errores de redondeo de punto flotante
   - Soporta valores hasta 99,999,999.99

3. **DATE para fechaEntrega**:
   - Solo almacena fecha, no hora
   - Suficiente para reportes diarios/semanales
   - Si se necesita timestamp exacto, considerar DATETIME

4. **VARCHAR con longitudes específicas**:
   - `tipoMaterial`: 50 caracteres (suficiente para nombres descriptivos)
   - `nombreProveedor`: 100 caracteres (nombres completos de empresas)
   - `correoElectronico`: 100 caracteres (estándar para emails)

### Escalabilidad

**Volumen estimado**:
- Materiales: ~100 registros (crece lentamente)
- Entregas: ~10,000+ registros/año (crece constantemente)
- Administradores: ~50 registros (crece lentamente)

**Optimizaciones futuras**:
- Particionamiento de tabla `Entregas` por fecha
- Archivado de entregas antiguas
- Índices adicionales según patrones de consulta

### Normalización

El esquema está en **Tercera Forma Normal (3NF)**:
- ✅ No hay grupos repetidos (1NF)
- ✅ Todas las columnas no-clave dependen de la clave completa (2NF)
- ✅ No hay dependencias transitivas (3NF)

---

## Migraciones

### Estrategia de Migración

**Desarrollo**:
```properties
spring.jpa.hibernate.ddl-auto=create
```
- Recrea el esquema en cada inicio
- Útil para desarrollo rápido
- ⚠️ **BORRA TODOS LOS DATOS**

**Producción**:
```properties
spring.jpa.hibernate.ddl-auto=validate
```
- Solo valida el esquema
- No hace cambios
- Requiere herramientas de migración (Flyway, Liquibase)

### Herramientas Recomendadas

**Flyway** (Recomendado para este proyecto):
```xml
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
```

**Liquibase** (Alternativa):
```xml
<dependency>
    <groupId>org.liquibase</groupId>
    <artifactId>liquibase-core</artifactId>
</dependency>
```

---

## Respaldo y Recuperación

### Respaldo de Base de Datos

**Comando mysqldump**:
```bash
mysqldump -u quintom -p PlantaReciclajeDB > backup_$(date +%Y%m%d).sql
```

**Respaldo de solo datos**:
```bash
mysqldump -u quintom -p --no-create-info PlantaReciclajeDB > data_backup.sql
```

**Respaldo de solo esquema**:
```bash
mysqldump -u quintom -p --no-data PlantaReciclajeDB > schema_backup.sql
```

### Restauración

```bash
mysql -u quintom -p PlantaReciclajeDB < backup_20251008.sql
```

---

## Monitoreo y Mantenimiento

### Consultas de Mantenimiento

**Verificar integridad referencial**:
```sql
SELECT e.idEntrega, e.idMaterial
FROM Entregas e
LEFT JOIN Materiales m ON e.idMaterial = m.idMaterial
WHERE m.idMaterial IS NULL;
```

**Estadísticas de tablas**:
```sql
SELECT 
    table_name AS 'Tabla',
    table_rows AS 'Filas',
    ROUND(((data_length + index_length) / 1024 / 1024), 2) AS 'Tamaño (MB)'
FROM information_schema.TABLES
WHERE table_schema = 'PlantaReciclajeDB'
ORDER BY (data_length + index_length) DESC;
```

---

## Conclusión

Este esquema de base de datos proporciona una base sólida para el sistema de gestión de planta de reciclaje. El diseño es:
- **Simple**: Fácil de entender y mantener
- **Normalizado**: Sin redundancia de datos
- **Escalable**: Puede crecer con el sistema
- **Flexible**: Permite extensiones futuras

Para más información sobre cómo interactuar con estos datos a través de la API, consultar `API_EXAMPLES.md`.
