# Guía de Configuración - Planta de Reciclaje

Esta guía proporciona instrucciones paso a paso para configurar el entorno de desarrollo del proyecto Planta de Reciclaje.

## Tabla de Contenidos

- [Requisitos del Sistema](#requisitos-del-sistema)
- [Instalación de Herramientas](#instalación-de-herramientas)
- [Configuración de MySQL](#configuración-de-mysql)
- [Configuración de IntelliJ IDEA](#configuración-de-intellij-idea)
- [Configuración del Proyecto](#configuración-del-proyecto)
- [Verificación de la Instalación](#verificación-de-la-instalación)
- [Solución de Problemas](#solución-de-problemas)

---

## Requisitos del Sistema

### Software Requerido

| Software | Versión Mínima | Versión Recomendada | Notas |
|----------|----------------|---------------------|-------|
| Java JDK | 21 | 21 | Oracle JDK o OpenJDK |
| Maven | 3.8 | 3.9+ | Incluido con IntelliJ IDEA |
| MySQL | 8.0 | 8.0+ | MySQL Community Server |
| IntelliJ IDEA | 2023.1 | 2025.2 | Community o Ultimate |
| Git | 2.30 | Latest | Para control de versiones |

### Especificaciones de Hardware

**Mínimas**:
- CPU: Dual-core
- RAM: 8 GB
- Disco: 10 GB libres

**Recomendadas**:
- CPU: Quad-core o superior
- RAM: 16 GB o más
- Disco: SSD con 20 GB libres

---

## Instalación de Herramientas

### 1. Instalación de Java 21

#### Windows

1. **Descargar**:
   - Oracle JDK: https://www.oracle.com/java/technologies/downloads/
   - OpenJDK (Recomendado): https://adoptium.net/

2. **Instalar**:
   - Ejecutar el instalador descargado
   - Seguir las instrucciones del asistente
   - Aceptar la configuración predeterminada

3. **Verificar instalación**:
```bash
java -version
javac -version
```

Salida esperada:
```
java version "21.0.x"
Java(TM) SE Runtime Environment (build 21.0.x+xx-xxx)
```

4. **Configurar JAVA_HOME** (si no se configuró automáticamente):
   - `Panel de Control` → `Sistema` → `Configuración avanzada del sistema`
   - `Variables de entorno` → `Nueva` (en Variables del sistema)
   - Nombre: `JAVA_HOME`
   - Valor: `C:\Program Files\Java\jdk-21` (ajustar según instalación)

#### macOS

1. **Usando Homebrew**:
```bash
brew install openjdk@21
```

2. **Configurar PATH**:
```bash
echo 'export PATH="/usr/local/opt/openjdk@21/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

3. **Verificar**:
```bash
java -version
```

#### Linux (Ubuntu/Debian)

1. **Instalar OpenJDK 21**:
```bash
sudo apt update
sudo apt install openjdk-21-jdk
```

2. **Verificar**:
```bash
java -version
javac -version
```

3. **Configurar JAVA_HOME** (agregar a `~/.bashrc` o `~/.zshrc`):
```bash
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
```

---

### 2. Instalación de MySQL

#### Windows

1. **Descargar MySQL Installer**:
   - https://dev.mysql.com/downloads/installer/

2. **Ejecutar el instalador**:
   - Seleccionar `MySQL Server`, `MySQL Workbench`, y `MySQL Shell`
   - Tipo de instalación: `Developer Default`

3. **Configurar MySQL Server**:
   - **Authentication Method**: Use Strong Password Encryption
   - **Root Password**: Establecer una contraseña segura
   - **User**: Crear usuario `quintom` con contraseña `admin` (o según preferencia)

4. **Iniciar MySQL**:
   - MySQL se inicia automáticamente como servicio de Windows
   - Verificar en `services.msc`: MySQL80 debe estar en estado "Running"

#### macOS

1. **Usando Homebrew**:
```bash
brew install mysql
```

2. **Iniciar MySQL**:
```bash
brew services start mysql
```

3. **Configuración inicial**:
```bash
mysql_secure_installation
```

4. **Crear usuario**:
```bash
mysql -u root -p
```
```sql
CREATE USER 'quintom'@'localhost' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON *.* TO 'quintom'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

#### Linux (Ubuntu/Debian)

1. **Instalar MySQL Server**:
```bash
sudo apt update
sudo apt install mysql-server
```

2. **Configurar MySQL**:
```bash
sudo mysql_secure_installation
```

3. **Iniciar MySQL**:
```bash
sudo systemctl start mysql
sudo systemctl enable mysql
```

4. **Crear usuario**:
```bash
sudo mysql
```
```sql
CREATE USER 'quintom'@'localhost' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON *.* TO 'quintom'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

#### Verificar Instalación de MySQL

```bash
mysql -u quintom -p
```
Ingresar contraseña: `admin`

Debería ver el prompt de MySQL:
```
mysql>
```

Salir con:
```sql
EXIT;
```

---

### 3. Instalación de IntelliJ IDEA

#### Descargar

**IntelliJ IDEA Community** (Gratis):
- https://www.jetbrains.com/idea/download/

**IntelliJ IDEA Ultimate** (Pago, incluye más características):
- https://www.jetbrains.com/idea/download/
- Prueba gratuita de 30 días disponible

#### Instalación

1. **Windows/macOS/Linux**:
   - Ejecutar el instalador descargado
   - Seguir las instrucciones del asistente
   - Opciones recomendadas:
     - ✓ Create Desktop Shortcut
     - ✓ Update PATH variable
     - ✓ Add "Open Folder as Project"
     - ✓ .java, .xml, .properties associations

2. **Primer inicio**:
   - Seleccionar tema (Light/Dark)
   - Instalar plugins recomendados cuando se solicite

---

## Configuración de MySQL

### Crear Base de Datos

El proyecto está configurado para crear automáticamente la base de datos si no existe, pero también puede crearla manualmente:

```bash
mysql -u quintom -p
```

```sql
CREATE DATABASE IF NOT EXISTS PlantaReciclajeDB;
USE PlantaReciclajeDB;
SHOW TABLES;  -- Debería estar vacío inicialmente
EXIT;
```

### Verificar Configuración de Conexión

Asegurarse de que MySQL está escuchando en el puerto correcto:

```sql
SHOW VARIABLES LIKE 'port';
```

Salida esperada:
```
+---------------+-------+
| Variable_name | Value |
+---------------+-------+
| port          | 3306  |
+---------------+-------+
```

---

## Configuración de IntelliJ IDEA

### 1. Abrir el Proyecto

1. **Iniciar IntelliJ IDEA**
2. **Abrir proyecto existente**:
   - `File` → `Open`
   - Navegar a la carpeta del proyecto
   - Seleccionar la carpeta `ProyectoFinal_PlantaDeReciclaje`
   - Click en `OK`

3. **Esperar a que Maven importe las dependencias**:
   - En la parte inferior derecha, verás "Importing Maven Projects..."
   - Esto puede tomar varios minutos la primera vez

### 2. Configurar el JDK del Proyecto

1. **Abrir Project Structure**:
   - `File` → `Project Structure` (o `Ctrl+Alt+Shift+S`)

2. **Project Settings → Project**:
   - **SDK**: Seleccionar `21` (o `Add SDK` → `Download JDK` si no está instalado)
   - **Language Level**: `21 - Pattern matching for switch, record patterns`
   - Click en `Apply`

3. **Platform Settings → SDKs**:
   - Verificar que JDK 21 está configurado correctamente
   - **JDK home path** debe apuntar a la instalación de Java 21

### 3. Configurar Maven

1. **Abrir Settings**:
   - `File` → `Settings` (Windows/Linux)
   - `IntelliJ IDEA` → `Preferences` (macOS)

2. **Build, Execution, Deployment → Build Tools → Maven**:
   - **Maven home directory**: Debería apuntar al Maven bundled (por defecto)
   - **User settings file**: Por defecto
   - **Local repository**: Por defecto (`~/.m2/repository`)

3. **Maven → Importing**:
   - ✓ **Import Maven projects automatically**
   - ✓ **Automatically download: Sources, Documentation**

### 4. Habilitar Procesamiento de Anotaciones

**Importante para Lombok y MapStruct**

1. **Abrir Settings**:
   - `File` → `Settings` (Windows/Linux)
   - `IntelliJ IDEA` → `Preferences` (macOS)

2. **Build, Execution, Deployment → Compiler → Annotation Processors**:
   - ✓ **Enable annotation processing**
   - **Store generated sources relative to**: `Module content root`
   - **Production sources directory**: `target/generated-sources/annotations`
   - Click en `Apply`

### 5. Instalar Plugins Requeridos

1. **Abrir Plugin Manager**:
   - `File` → `Settings` → `Plugins`

2. **Instalar plugins** (si no están instalados):
   - **Lombok**: Buscar "Lombok" en Marketplace → Install
   - **Database Tools** (incluido en Ultimate, limitado en Community)
   - Click en `OK` y reiniciar IDEA si se solicita

### 6. Configurar Conexión a Base de Datos (Opcional)

Solo disponible en IntelliJ IDEA Ultimate o con el plugin Database Navigator.

1. **Abrir Database Tool Window**:
   - `View` → `Tool Windows` → `Database`

2. **Agregar Data Source**:
   - Click en `+` → `Data Source` → `MySQL`

3. **Configurar conexión**:
   - **Host**: `localhost`
   - **Port**: `3306`
   - **Database**: `PlantaReciclajeDB`
   - **User**: `quintom`
   - **Password**: `admin`
   - Click en `Test Connection` para verificar
   - Click en `OK`

---

## Configuración del Proyecto

### 1. Verificar application.properties

Ubicación: `src/main/resources/application.properties`

**Verificar/actualizar la configuración**:

```properties
spring.application.name=planta-de-reciclaje

# Configuración del servidor
server.servlet.context-path=/plantaReciclaje/api

# Configuración de la base de datos
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

**Ajustes necesarios**:
- Si el usuario de MySQL es diferente, actualizar `spring.datasource.username`
- Si la contraseña es diferente, actualizar `spring.datasource.password`
- Si MySQL está en un puerto diferente, actualizar en `spring.datasource.url`

### 2. Compilar el Proyecto

#### Desde IntelliJ IDEA

1. **Abrir Maven Tool Window**:
   - `View` → `Tool Windows` → `Maven`

2. **Ejecutar goals**:
   - Expandir `Lifecycle`
   - Doble-click en `clean`
   - Doble-click en `compile`

O usar el menú:
- `Build` → `Build Project` (o `Ctrl+F9`)

#### Desde Terminal

```bash
./mvnw clean compile
```

**Windows**:
```bash
mvnw.cmd clean compile
```

### 3. Ejecutar la Aplicación

#### Desde IntelliJ IDEA

1. **Localizar la clase principal**:
   - `PlantaDeReciclajeApplication.java`

2. **Ejecutar**:
   - Click derecho en la clase → `Run 'PlantaDeReciclajeApplication'`
   - O click en el ícono verde ▶ junto a la línea del método `main`

3. **Verificar la salida en la consola**:
   - Debe ver logs de Spring Boot inicializándose
   - Buscar: `Started PlantaDeReciclajeApplication in X seconds`

#### Desde Terminal

```bash
./mvnw spring-boot:run
```

---

## Verificación de la Instalación

### 1. Verificar que la Aplicación Está Funcionando

**Abrir navegador o usar curl**:

```bash
curl http://localhost:8080/plantaReciclaje/api/entregas
```

**Salida esperada**: JSON con lista de entregas (puede estar vacía si es la primera ejecución sin datos de ejemplo).

### 2. Verificar Swagger UI

**Abrir en navegador**:
```
http://localhost:8080/plantaReciclaje/api/swagger-ui/index.html
```

Debería ver la interfaz de Swagger con la documentación de la API.

### 3. Verificar Base de Datos

```bash
mysql -u quintom -p PlantaReciclajeDB
```

```sql
SHOW TABLES;
```

**Salida esperada**:
```
+----------------------------+
| Tables_in_PlantaReciclajeDB|
+----------------------------+
| administradores            |
| entregas                   |
| materiales                 |
+----------------------------+
```

```sql
SELECT COUNT(*) FROM Materiales;
SELECT COUNT(*) FROM Entregas;
SELECT COUNT(*) FROM Administradores;
```

Debería ver datos si `data.sql` se ejecutó correctamente.

### 4. Probar un Endpoint

**Crear una nueva entrega**:

```bash
curl -X POST http://localhost:8080/plantaReciclaje/api/entregas \
  -H "Content-Type: application/json" \
  -d '{
    "cantidadKg": 10.00,
    "nombreProveedor": "Test Proveedor",
    "idMaterial": 1
  }'
```

**Salida esperada**: `201 Created` con los datos de la entrega creada.

---

## Solución de Problemas

### Problema 1: Java Version Mismatch

**Error**:
```
error: release version 21 not supported
```

**Solución**:
1. Verificar versión de Java:
   ```bash
   java -version
   ```
2. Si es menor a 21, instalar Java 21
3. Configurar JDK en IntelliJ: `File` → `Project Structure` → `Project` → `SDK`

---

### Problema 2: MySQL Connection Failed

**Error**:
```
Communications link failure
```

**Soluciones**:

1. **Verificar que MySQL está en ejecución**:
   - Windows: `services.msc` → MySQL80 debe estar "Running"
   - macOS: `brew services list` → mysql debe estar "started"
   - Linux: `sudo systemctl status mysql` → debe estar "active (running)"

2. **Verificar puerto**:
   ```bash
   mysql -u quintom -p -h 127.0.0.1 -P 3306
   ```

3. **Verificar usuario y contraseña**:
   ```bash
   mysql -u quintom -p
   ```
   - Si no funciona, recrear el usuario

4. **Verificar `application.properties`**:
   - Usuario, contraseña, puerto correcto

---

### Problema 3: Access Denied for User

**Error**:
```
Access denied for user 'quintom'@'localhost'
```

**Solución**:

1. **Recrear usuario**:
```bash
mysql -u root -p
```
```sql
DROP USER IF EXISTS 'quintom'@'localhost';
CREATE USER 'quintom'@'localhost' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON *.* TO 'quintom'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

2. **Actualizar contraseña en `application.properties`**

---

### Problema 4: Port 8080 Already in Use

**Error**:
```
Web server failed to start. Port 8080 was already in use.
```

**Soluciones**:

1. **Cambiar el puerto en `application.properties`**:
```properties
server.port=8081
```

2. **O encontrar y detener el proceso que usa el puerto**:
   - Windows:
     ```bash
     netstat -ano | findstr :8080
     taskkill /PID <PID> /F
     ```
   - macOS/Linux:
     ```bash
     lsof -i :8080
     kill -9 <PID>
     ```

---

### Problema 5: Annotation Processing Not Working

**Síntomas**: Lombok o MapStruct no funcionan (getters/setters no encontrados)

**Solución**:

1. **Habilitar procesamiento de anotaciones**:
   - `Settings` → `Build, Execution, Deployment` → `Compiler` → `Annotation Processors`
   - ✓ `Enable annotation processing`

2. **Reinstalar plugins**:
   - `Settings` → `Plugins` → Buscar "Lombok" → Reinstall

3. **Rebuild project**:
   - `Build` → `Rebuild Project`

---

### Problema 6: Maven Dependencies Not Downloaded

**Síntomas**: Clases de Spring Boot no se encuentran

**Solución**:

1. **Reimportar proyecto Maven**:
   - Maven Tool Window → Click en 🔄 (Reload All Maven Projects)

2. **O desde terminal**:
   ```bash
   ./mvnw clean install -U
   ```

3. **Invalidar caches**:
   - `File` → `Invalidate Caches / Restart` → `Invalidate and Restart`

---

### Problema 7: Table Doesn't Exist

**Error**:
```
Table 'PlantaReciclajeDB.entregas' doesn't exist
```

**Solución**:

1. **Verificar `spring.jpa.hibernate.ddl-auto`**:
   - Debe ser `create` para desarrollo
   
2. **Reiniciar la aplicación** (las tablas se crean al iniciar)

3. **O crear manualmente**:
   ```bash
   mysql -u quintom -p PlantaReciclajeDB < Database/PlantaReciclajeDb.sql
   ```

---

## Configuración Avanzada

### Configuración de Logging

Agregar a `application.properties` para más detalles:

```properties
# Logging
logging.level.root=INFO
logging.level.com.oxo.planta_de_reciclaje=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

### Hot Reload con Spring Boot DevTools

1. **Agregar dependencia en `pom.xml`**:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

2. **Habilitar auto-build en IntelliJ**:
   - `Settings` → `Build, Execution, Deployment` → `Compiler`
   - ✓ `Build project automatically`

3. **Habilitar Run en modo Debug para hot reload**

---

## Próximos Pasos

Una vez configurado el entorno:

1. ✅ Explorar la documentación en `README.md`
2. ✅ Revisar ejemplos de API en `docs/API_EXAMPLES.md`
3. ✅ Familiarizarse con el esquema de base de datos en `docs/DATABASE_SCHEMA.md`
4. ✅ Probar endpoints usando Swagger UI
5. ✅ Implementar nuevas funcionalidades

---

## Recursos Adicionales

- **Spring Boot Documentation**: https://docs.spring.io/spring-boot/docs/current/reference/html/
- **Spring Data JPA**: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
- **MySQL Documentation**: https://dev.mysql.com/doc/
- **IntelliJ IDEA Documentation**: https://www.jetbrains.com/help/idea/
- **Lombok**: https://projectlombok.org/
- **MapStruct**: https://mapstruct.org/

---

## Contacto y Soporte

Para problemas no cubiertos en esta guía:
1. Revisar la sección de [Issues](https://github.com/usuario/proyecto/issues) del repositorio
2. Consultar con el equipo de desarrollo
3. Verificar logs de la aplicación en la consola de IntelliJ

---

**¡Feliz desarrollo! 🚀**
