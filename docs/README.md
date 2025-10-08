# Documentación del Proyecto - Planta de Reciclaje

Bienvenido a la documentación completa del proyecto **Planta de Reciclaje**. Esta carpeta contiene toda la documentación técnica necesaria para entender, configurar y utilizar el sistema.

## 📚 Índice de Documentación

### 🏠 [README.md Principal](../README.md)
**Documentación completa del proyecto**

Contenido:
- Introducción y propósito del proyecto
- Componentes principales del sistema
- Arquitectura detallada (capas, estructura de directorios)
- Configuración del entorno de desarrollo
- Dependencias del proyecto (Spring Boot, MySQL, Lombok, MapStruct)
- API REST completa con ejemplos
- Generación de reportes (diarios y semanales)
- Configuración de base de datos
- Validación de entradas
- Pruebas
- Documentación OpenAPI/Swagger

**👉 Comienza aquí si es tu primera vez con el proyecto**

---

### 🚀 [SETUP_GUIDE.md](./SETUP_GUIDE.md)
**Guía de Configuración Paso a Paso**

Contenido:
- Requisitos del sistema
- Instalación de Java 21 (Windows, macOS, Linux)
- Instalación y configuración de MySQL
- Configuración de IntelliJ IDEA 2025.2
- Configuración del proyecto
- Verificación de la instalación
- Solución de problemas comunes
- Configuración avanzada (logging, hot reload)

**👉 Usa esta guía para configurar tu entorno de desarrollo**

---

### 🔌 [API_EXAMPLES.md](./API_EXAMPLES.md)
**Ejemplos Completos de Uso de la API**

Contenido:
- Configuración inicial (URL base, headers)
- Ejemplos detallados de todos los endpoints:
  - GET /entregas (listar todas)
  - GET /entregas/{id} (obtener por ID)
  - POST /entregas (crear nueva)
  - PUT /entregas/{id} (modificar)
  - DELETE /entregas/{id} (eliminar)
  - GET /entregas/reporte (reportes diario/semanal)
- Ejemplos con curl
- Ejemplos con Postman (incluyendo tests)
- Manejo de errores y validaciones
- Flujos de trabajo completos
- Referencia rápida de materiales

**👉 Consulta esta guía para aprender a usar la API con ejemplos prácticos**

---

### 🗄️ [DATABASE_SCHEMA.md](./DATABASE_SCHEMA.md)
**Esquema Completo de Base de Datos**

Contenido:
- Diagrama entidad-relación (ER)
- Descripción detallada de todas las tablas:
  - Materiales
  - Entregas
  - Administradores
- Campos, tipos de datos y constraints
- Relaciones y claves foráneas
- Índices y optimizaciones
- Scripts de inicialización
- Datos de ejemplo
- Consultas SQL útiles
- Consideraciones de diseño y escalabilidad
- Estrategias de migración
- Respaldo y recuperación

**👉 Consulta esta guía para entender la estructura de datos**

---

## 🎯 Flujo de Trabajo Recomendado

### Para Nuevos Desarrolladores

1. **Leer el README principal** ([README.md](../README.md))
   - Entender el propósito y arquitectura del proyecto

2. **Seguir la guía de configuración** ([SETUP_GUIDE.md](./SETUP_GUIDE.md))
   - Instalar herramientas necesarias
   - Configurar el entorno de desarrollo
   - Ejecutar la aplicación por primera vez

3. **Explorar el esquema de base de datos** ([DATABASE_SCHEMA.md](./DATABASE_SCHEMA.md))
   - Familiarizarse con las tablas y relaciones
   - Entender el modelo de datos

4. **Probar la API** ([API_EXAMPLES.md](./API_EXAMPLES.md))
   - Ejecutar ejemplos con curl o Postman
   - Entender los endpoints disponibles
   - Practicar con diferentes casos de uso

5. **Acceder a Swagger UI**
   ```
   http://localhost:8080/plantaReciclaje/api/swagger-ui/index.html
   ```
   - Explorar la documentación interactiva
   - Probar endpoints en tiempo real

### Para Desarrolladores Experimentados

1. **Revisión rápida**: Leer [README.md](../README.md) (secciones de arquitectura y API)
2. **Configuración**: Seguir [SETUP_GUIDE.md](./SETUP_GUIDE.md) (solo secciones necesarias)
3. **Desarrollo**: Consultar [API_EXAMPLES.md](./API_EXAMPLES.md) y [DATABASE_SCHEMA.md](./DATABASE_SCHEMA.md) según necesidad

---

## 🔍 Búsqueda Rápida

### ¿Cómo hacer...?

| Tarea | Documento | Sección |
|-------|-----------|---------|
| Instalar Java 21 | [SETUP_GUIDE.md](./SETUP_GUIDE.md) | Instalación de Herramientas → Java 21 |
| Configurar MySQL | [SETUP_GUIDE.md](./SETUP_GUIDE.md) | Instalación de Herramientas → MySQL |
| Configurar IntelliJ IDEA | [SETUP_GUIDE.md](./SETUP_GUIDE.md) | Configuración de IntelliJ IDEA |
| Crear una entrega | [API_EXAMPLES.md](./API_EXAMPLES.md) | Ejemplos de Entregas → Crear Nueva |
| Generar reporte diario | [README.md](../README.md) | Generación de Reportes → Reporte Diario |
| Generar reporte semanal | [API_EXAMPLES.md](./API_EXAMPLES.md) | Ejemplos de Reportes → Reporte Semanal |
| Ver estructura de tabla | [DATABASE_SCHEMA.md](./DATABASE_SCHEMA.md) | Tablas → [nombre de tabla] |
| Solucionar error de conexión | [SETUP_GUIDE.md](./SETUP_GUIDE.md) | Solución de Problemas → MySQL Connection |
| Ver ejemplos con Postman | [API_EXAMPLES.md](./API_EXAMPLES.md) | Ejemplos con Postman |
| Entender validaciones | [README.md](../README.md) | Validación de Entradas |

---

## 📖 Contenido por Tema

### Arquitectura y Diseño
- **README.md**: Arquitectura del Proyecto, Capas del Proyecto
- **DATABASE_SCHEMA.md**: Diagrama ER, Relaciones, Consideraciones de Diseño

### Configuración y Setup
- **SETUP_GUIDE.md**: Completo (instalación y configuración)
- **README.md**: Configuración del Entorno de Desarrollo

### API y Endpoints
- **README.md**: API REST (descripción de endpoints)
- **API_EXAMPLES.md**: Ejemplos completos con curl y Postman

### Base de Datos
- **DATABASE_SCHEMA.md**: Esquema completo
- **README.md**: Configuración de la Base de Datos

### Reportes
- **README.md**: Generación de Reportes (lógica y parámetros)
- **API_EXAMPLES.md**: Ejemplos de Reportes (casos de uso)

### Validación y Errores
- **README.md**: Validación de Entradas
- **API_EXAMPLES.md**: Manejo de Errores (ejemplos de validación fallida)

### Solución de Problemas
- **SETUP_GUIDE.md**: Solución de Problemas (7 problemas comunes)

---

## 🛠️ Recursos Adicionales

### Herramientas de Desarrollo

**Swagger UI** (Documentación Interactiva):
```
http://localhost:8080/plantaReciclaje/api/swagger-ui/index.html
```

**OpenAPI Spec** (JSON):
```
http://localhost:8080/plantaReciclaje/api/v3/api-docs
```

### Enlaces Externos

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [IntelliJ IDEA Help](https://www.jetbrains.com/help/idea/)
- [Lombok Features](https://projectlombok.org/features/all)
- [MapStruct Documentation](https://mapstruct.org/documentation/stable/reference/html/)
- [SpringDoc OpenAPI](https://springdoc.org/)

---

## 📋 Checklist de Documentación

### Para Nuevos Desarrolladores

- [ ] Leer README.md principal
- [ ] Instalar Java 21
- [ ] Instalar MySQL
- [ ] Instalar IntelliJ IDEA
- [ ] Configurar el proyecto
- [ ] Ejecutar la aplicación
- [ ] Verificar Swagger UI
- [ ] Probar un endpoint con curl
- [ ] Explorar la base de datos
- [ ] Crear una entrega de prueba
- [ ] Generar un reporte

### Para Contribuidores

- [ ] Entender la arquitectura del proyecto
- [ ] Familiarizarse con las capas (Controller, Service, Repository)
- [ ] Conocer el esquema de base de datos
- [ ] Entender el flujo de validación
- [ ] Revisar convenciones de código
- [ ] Conocer los DTOs y Entities
- [ ] Entender MapStruct y Lombok

---

## 📝 Convenciones de Documentación

### Formato de Código

**Bloques de código con lenguaje especificado**:
```java
// Código Java
```

```sql
-- Código SQL
```

```bash
# Comandos de terminal
```

### Iconos Utilizados

- ✅ Completado/Correcto
- ❌ Error/Incorrecto
- ⚠️ Advertencia/Importante
- 💡 Consejo/Tip
- 📝 Nota
- 🔍 Ver también

### Niveles de Encabezados

- `#` - Título del documento
- `##` - Sección principal
- `###` - Subsección
- `####` - Detalle de subsección

---

## 🤝 Contribuir a la Documentación

Si encuentras errores o deseas mejorar la documentación:

1. **Para correcciones menores**:
   - Editar directamente el archivo correspondiente
   - Crear un pull request con los cambios

2. **Para adiciones mayores**:
   - Discutir primero con el equipo
   - Mantener el mismo formato y estilo
   - Actualizar este índice si se agregan nuevos documentos

3. **Lineamientos**:
   - Escribir en español claro y conciso
   - Incluir ejemplos prácticos
   - Mantener coherencia con documentación existente
   - Agregar enlaces cruzados cuando sea relevante

---

## 📞 Soporte

Para preguntas o problemas con la documentación:

1. Revisar la sección correspondiente en los documentos
2. Consultar la sección de "Solución de Problemas" en [SETUP_GUIDE.md](./SETUP_GUIDE.md)
3. Buscar en el README principal
4. Contactar al equipo de desarrollo

---

## 📅 Historial de Versiones de Documentación

| Versión | Fecha | Cambios |
|---------|-------|---------|
| 1.0.0 | 2025-10-08 | Documentación inicial completa |

---

## 📜 Licencia

Esta documentación es parte del proyecto Planta de Reciclaje y está sujeta a la misma licencia que el código fuente.

---

**¡Gracias por usar Planta de Reciclaje! 🌱♻️**

Para comenzar, visita el [README principal](../README.md) o sigue la [Guía de Configuración](./SETUP_GUIDE.md).
