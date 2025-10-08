# Ejemplos de Uso de la API - Planta de Reciclaje

Este documento proporciona ejemplos detallados de cómo usar la API REST de Planta de Reciclaje, incluyendo solicitudes y respuestas completas.

## Tabla de Contenidos

- [Configuración Inicial](#configuración-inicial)
- [Ejemplos de Entregas](#ejemplos-de-entregas)
- [Ejemplos de Reportes](#ejemplos-de-reportes)
- [Ejemplos con Postman](#ejemplos-con-postman)
- [Manejo de Errores](#manejo-de-errores)

---

## Configuración Inicial

### URL Base

Todas las peticiones deben dirigirse a:
```
http://localhost:8080/plantaReciclaje/api
```

### Headers Requeridos

Para peticiones POST y PUT:
```
Content-Type: application/json
```

---

## Ejemplos de Entregas

### 1. Listar Todas las Entregas

#### Request
```bash
curl -X GET http://localhost:8080/plantaReciclaje/api/entregas
```

#### Response (200 OK)
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
  },
  {
    "idEntrega": 3,
    "cantidadKg": 15.75,
    "fechaEntrega": "2025-10-08",
    "nombreProveedor": "Reciclajes San Juan",
    "compensacion": 9.61,
    "idMaterial": 3
  }
]
```

---

### 2. Obtener una Entrega Específica

#### Request
```bash
curl -X GET http://localhost:8080/plantaReciclaje/api/entregas/1
```

#### Response Exitosa (200 OK)
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

#### Response - Entrega No Encontrada (404 Not Found)
```
(Sin cuerpo de respuesta)
```

---

### 3. Crear una Nueva Entrega - Cartón

#### Request
```bash
curl -X POST http://localhost:8080/plantaReciclaje/api/entregas \
  -H "Content-Type: application/json" \
  -d '{
    "cantidadKg": 50.00,
    "nombreProveedor": "Recicladora Centro",
    "idMaterial": 1
  }'
```

#### Response (201 Created)
```json
{
  "idEntrega": 21,
  "cantidadKg": 50.00,
  "fechaEntrega": "2025-10-08",
  "nombreProveedor": "Recicladora Centro",
  "compensacion": 6.00,
  "idMaterial": 1
}
```

**Notas**:
- El campo `compensacion` se calculó automáticamente: 50.00 kg × $0.12/kg = $6.00
- El campo `fechaEntrega` se estableció automáticamente a la fecha actual
- El campo `idEntrega` fue generado automáticamente por la base de datos

---

### 4. Crear una Nueva Entrega - Cobre

#### Request
```bash
curl -X POST http://localhost:8080/plantaReciclaje/api/entregas \
  -H "Content-Type: application/json" \
  -d '{
    "cantidadKg": 10.50,
    "nombreProveedor": "Metales Preciosos GT",
    "idMaterial": 5
  }'
```

#### Response (201 Created)
```json
{
  "idEntrega": 22,
  "cantidadKg": 10.50,
  "fechaEntrega": "2025-10-08",
  "nombreProveedor": "Metales Preciosos GT",
  "compensacion": 84.00,
  "idMaterial": 5
}
```

**Notas**:
- Compensación: 10.50 kg × $8.00/kg = $84.00
- El cobre tiene un precio mucho mayor que otros materiales

---

### 5. Crear una Nueva Entrega - Validación Fallida

#### Request - Cantidad Nula
```bash
curl -X POST http://localhost:8080/plantaReciclaje/api/entregas \
  -H "Content-Type: application/json" \
  -d '{
    "nombreProveedor": "Recicladora Centro",
    "idMaterial": 1
  }'
```

#### Response (400 Bad Request)
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

#### Request - Cantidad Negativa
```bash
curl -X POST http://localhost:8080/plantaReciclaje/api/entregas \
  -H "Content-Type: application/json" \
  -d '{
    "cantidadKg": -25.00,
    "nombreProveedor": "Recicladora Centro",
    "idMaterial": 1
  }'
```

#### Response (400 Bad Request)
```json
{
  "timestamp": "2025-10-08T16:30:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "La cantidad ingresada debe ser positiva",
  "path": "/plantaReciclaje/api/entregas"
}
```

---

### 6. Modificar una Entrega

#### Request
```bash
curl -X PUT http://localhost:8080/plantaReciclaje/api/entregas/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nombreProveedor": "Recicladora El Progreso - Sucursal Central"
  }'
```

#### Response (200 OK)
```json
{
  "idEntrega": 1,
  "cantidadKg": 45.50,
  "fechaEntrega": "2025-10-08",
  "nombreProveedor": "Recicladora El Progreso - Sucursal Central",
  "compensacion": 5.46,
  "idMaterial": 1
}
```

---

#### Request - Nombre Vacío
```bash
curl -X PUT http://localhost:8080/plantaReciclaje/api/entregas/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nombreProveedor": ""
  }'
```

#### Response (400 Bad Request)
```json
{
  "timestamp": "2025-10-08T16:30:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "El nombre en este campo es obligatorio",
  "path": "/plantaReciclaje/api/entregas/1"
}
```

---

### 7. Eliminar una Entrega

#### Request
```bash
curl -X DELETE http://localhost:8080/plantaReciclaje/api/entregas/1
```

#### Response Exitosa (204 No Content)
```
(Sin cuerpo de respuesta)
```

#### Response - Entrega No Encontrada (404 Not Found)
```
(Sin cuerpo de respuesta)
```

---

## Ejemplos de Reportes

### 1. Reporte Diario

Obtiene todas las entregas realizadas en una fecha específica.

#### Request
```bash
curl -X GET "http://localhost:8080/plantaReciclaje/api/entregas/reporte?fecha=2025-10-08&tipo=diario"
```

#### Response (200 OK)
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
  },
  {
    "idEntrega": 3,
    "cantidadKg": 15.75,
    "fechaEntrega": "2025-10-08",
    "nombreProveedor": "Reciclajes San Juan",
    "compensacion": 9.61,
    "idMaterial": 3
  }
]
```

**Nota**: Solo retorna entregas del día 2025-10-08.

---

### 2. Reporte Semanal

Obtiene todas las entregas realizadas en la semana de una fecha específica (de lunes a domingo).

#### Request
```bash
curl -X GET "http://localhost:8080/plantaReciclaje/api/entregas/reporte?fecha=2025-10-08&tipo=semanal"
```

#### Response (200 OK)
```json
[
  {
    "idEntrega": 1,
    "cantidadKg": 45.50,
    "fechaEntrega": "2025-10-06",
    "nombreProveedor": "Recicladora El Progreso",
    "compensacion": 5.46,
    "idMaterial": 1
  },
  {
    "idEntrega": 2,
    "cantidadKg": 30.00,
    "fechaEntrega": "2025-10-07",
    "nombreProveedor": "Papeles del Norte",
    "compensacion": 8.10,
    "idMaterial": 2
  },
  {
    "idEntrega": 3,
    "cantidadKg": 15.75,
    "fechaEntrega": "2025-10-08",
    "nombreProveedor": "Reciclajes San Juan",
    "compensacion": 9.61,
    "idMaterial": 3
  },
  {
    "idEntrega": 4,
    "cantidadKg": 22.00,
    "fechaEntrega": "2025-10-09",
    "nombreProveedor": "Metal Reusado GT",
    "compensacion": 27.06,
    "idMaterial": 4
  }
]
```

**Nota**: 
- Si `fecha=2025-10-08` (miércoles), el reporte incluye entregas desde el lunes 2025-10-06 hasta el domingo 2025-10-12.
- La semana se calcula automáticamente basándose en la fecha proporcionada.

---

### 3. Reporte con Fecha Futura

#### Request
```bash
curl -X GET "http://localhost:8080/plantaReciclaje/api/entregas/reporte?fecha=2025-12-25&tipo=diario"
```

#### Response (200 OK)
```json
[]
```

**Nota**: Si no hay entregas en la fecha especificada, se retorna una lista vacía.

---

### 4. Reporte con Tipo Inválido

#### Request
```bash
curl -X GET "http://localhost:8080/plantaReciclaje/api/entregas/reporte?fecha=2025-10-08&tipo=mensual"
```

#### Response (400 Bad Request)
```
(Sin cuerpo de respuesta)
```

**Nota**: Solo se aceptan los tipos `diario` y `semanal`.

---

### 5. Reporte con Formato de Fecha Inválido

#### Request
```bash
curl -X GET "http://localhost:8080/plantaReciclaje/api/entregas/reporte?fecha=08/10/2025&tipo=diario"
```

#### Response (400 Bad Request)
```json
{
  "timestamp": "2025-10-08T16:30:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Failed to convert value of type 'java.lang.String' to required type 'java.time.LocalDate'",
  "path": "/plantaReciclaje/api/entregas/reporte"
}
```

**Nota**: La fecha debe estar en formato ISO 8601: `YYYY-MM-DD`

---

## Ejemplos con Postman

### Configuración de Colección en Postman

#### 1. Crear Nueva Colección

1. Abrir Postman
2. Clic en **"New"** → **"Collection"**
3. Nombre: `Planta de Reciclaje API`

#### 2. Configurar Variables de Colección

1. Clic derecho en la colección → **"Edit"**
2. Ir a la pestaña **"Variables"**
3. Agregar variable:
   - **Variable**: `baseUrl`
   - **Initial Value**: `http://localhost:8080/plantaReciclaje/api`
   - **Current Value**: `http://localhost:8080/plantaReciclaje/api`
4. **Save**

---

### Request 1: Listar Todas las Entregas

1. **Método**: `GET`
2. **URL**: `{{baseUrl}}/entregas`
3. **Headers**: Ninguno requerido
4. **Body**: Ninguno
5. **Expected Response**: `200 OK` con lista de entregas

---

### Request 2: Crear Nueva Entrega de Papel Blanco

1. **Método**: `POST`
2. **URL**: `{{baseUrl}}/entregas`
3. **Headers**:
   - `Content-Type`: `application/json`
4. **Body** (raw JSON):
```json
{
  "cantidadKg": 75.00,
  "nombreProveedor": "Papelería Central",
  "idMaterial": 2
}
```
5. **Expected Response**: `201 Created`

**Tests** (pestaña Tests en Postman):
```javascript
pm.test("Status code is 201", function () {
    pm.response.to.have.status(201);
});

pm.test("Response has idEntrega", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData).to.have.property("idEntrega");
});

pm.test("Compensacion is calculated", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData.compensacion).to.eql(20.25); // 75 kg × 0.27 = 20.25
});
```

---

### Request 3: Generar Reporte Semanal

1. **Método**: `GET`
2. **URL**: `{{baseUrl}}/entregas/reporte`
3. **Params**:
   - Key: `fecha`, Value: `2025-10-08`
   - Key: `tipo`, Value: `semanal`
4. **Expected Response**: `200 OK` con lista de entregas de la semana

**Tests**:
```javascript
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Response is an array", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData).to.be.an('array');
});
```

---

### Request 4: Modificar Proveedor de Entrega

1. **Método**: `PUT`
2. **URL**: `{{baseUrl}}/entregas/1`
3. **Headers**:
   - `Content-Type`: `application/json`
4. **Body** (raw JSON):
```json
{
  "nombreProveedor": "Recicladora El Progreso - Actualizado"
}
```
5. **Expected Response**: `200 OK`

---

### Request 5: Eliminar Entrega

1. **Método**: `DELETE`
2. **URL**: `{{baseUrl}}/entregas/5`
3. **Expected Response**: `204 No Content`

**Tests**:
```javascript
pm.test("Status code is 204", function () {
    pm.response.to.have.status(204);
});
```

---

## Manejo de Errores

### Errores Comunes y Soluciones

#### 1. Error 400 - Bad Request

**Causa**: Datos de entrada inválidos o parámetros incorrectos.

**Ejemplos**:
- Campos obligatorios faltantes
- Valores negativos donde se requieren positivos
- Formato de fecha incorrecto
- Tipo de reporte inválido

**Solución**: Verificar que los datos de entrada cumplan con las validaciones requeridas.

---

#### 2. Error 404 - Not Found

**Causa**: El recurso solicitado no existe.

**Ejemplos**:
- Intentar obtener una entrega con un ID que no existe
- Intentar modificar o eliminar una entrega inexistente

**Solución**: Verificar que el ID del recurso sea correcto y exista en la base de datos.

---

#### 3. Error 500 - Internal Server Error

**Causa**: Error del servidor (ej: problema de base de datos).

**Ejemplos**:
- MySQL no está en ejecución
- Problema de conexión a la base de datos
- Error en la lógica de negocio

**Solución**: 
- Verificar logs del servidor
- Asegurar que MySQL esté en ejecución
- Revisar configuración de conexión en `application.properties`

---

## Flujos de Trabajo Completos

### Flujo 1: Registrar una Nueva Entrega y Verificar

1. **Paso 1**: Crear la entrega
```bash
curl -X POST http://localhost:8080/plantaReciclaje/api/entregas \
  -H "Content-Type: application/json" \
  -d '{
    "cantidadKg": 100.00,
    "nombreProveedor": "EcoReciclaje GT",
    "idMaterial": 4
  }'
```

2. **Paso 2**: Guardar el `idEntrega` de la respuesta (ej: 23)

3. **Paso 3**: Verificar que se creó correctamente
```bash
curl -X GET http://localhost:8080/plantaReciclaje/api/entregas/23
```

4. **Paso 4**: (Opcional) Listar todas las entregas para verificar
```bash
curl -X GET http://localhost:8080/plantaReciclaje/api/entregas
```

---

### Flujo 2: Generar Reporte y Analizar Resultados

1. **Paso 1**: Generar reporte semanal
```bash
curl -X GET "http://localhost:8080/plantaReciclaje/api/entregas/reporte?fecha=2025-10-08&tipo=semanal" \
  -o reporte_semanal.json
```

2. **Paso 2**: Analizar el archivo JSON generado
```bash
cat reporte_semanal.json | jq '.[] | {proveedor: .nombreProveedor, compensacion: .compensacion}'
```

3. **Paso 3**: Calcular total de compensaciones (usando jq)
```bash
cat reporte_semanal.json | jq '[.[].compensacion] | add'
```

---

### Flujo 3: Actualizar Información de Proveedor

1. **Paso 1**: Obtener la entrega actual
```bash
curl -X GET http://localhost:8080/plantaReciclaje/api/entregas/5
```

2. **Paso 2**: Modificar el nombre del proveedor
```bash
curl -X PUT http://localhost:8080/plantaReciclaje/api/entregas/5 \
  -H "Content-Type: application/json" \
  -d '{
    "nombreProveedor": "Nombre Actualizado"
  }'
```

3. **Paso 3**: Verificar la modificación
```bash
curl -X GET http://localhost:8080/plantaReciclaje/api/entregas/5
```

---

## Referencia Rápida de Materiales

| ID | Material | Precio por kg |
|----|----------|---------------|
| 1 | Cartón | $0.12 |
| 2 | Papel Blanco | $0.27 |
| 3 | PET Cristal | $0.61 |
| 4 | Aluminio Latas | $1.23 |
| 5 | Cobre | $8.00 |
| 6 | Chatarra Fierro | $0.16 |
| 7 | Vidrio | $0.05 |
| 8 | Polietileno Alta Densidad | $0.63 |
| 9 | Polietileno Baja Densidad | $0.29 |
| 10 | Polipropileno | $0.23 |

Para la lista completa, consultar la tabla `Materiales` en la base de datos.

---

## Conclusión

Este documento proporciona ejemplos prácticos para interactuar con la API de Planta de Reciclaje. Para más información sobre la arquitectura del sistema y configuración, consultar el archivo `README.md` principal del proyecto.
