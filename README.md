# SACE - Sistema de Administración de Empleados

Proyecto final del **Grupo 6** para la asignatura **Programación Orientada a Objetos** de la Universidad de El Salvador en Línea.

## 1. Descripción

SACE es una aplicación de escritorio desarrollada en Java que permite administrar empleados y calcular sus remuneraciones de acuerdo con tres modalidades de contratación:

- Administrativo.
- Por hora.
- Por comisión.

La aplicación utiliza **Java Swing** para la interfaz gráfica, **MongoDB** para persistencia y una arquitectura separada en modelo, DAO, servicios, vistas, configuración y utilidades.

## 2. Tecnologías y versiones

- JDK 21.
- Apache NetBeans 22.
- Java Swing.
- Apache Maven.
- MongoDB Community Server o MongoDB Atlas.
- MongoDB Java Sync Driver 4.11.1.
- Git y GitHub.

## 3. Funcionalidades principales

- Inicio de sesión mediante usuario o correo y contraseña.
- Validación de credenciales almacenadas en MongoDB.
- Contraseñas protegidas con PBKDF2-HMAC-SHA256 y salt.
- Cierre de sesión.
- Dashboard con indicadores de empleados activos, tipos y nómina actual.
- Registro de empleados administrativos, por hora y por comisión.
- Consulta, búsqueda y modificación de empleados.
- Inactivación de empleados sin eliminar su historial.
- Filtro y consulta por tipo de empleado.
- Cálculo polimórfico de remuneraciones.
- Registro del cálculo por período.
- Historial de remuneraciones.
- Reportes por tipo y resumen de remuneraciones.
- Validaciones de campos obligatorios, duplicados, fechas, valores negativos y rangos.

## 4. Fórmulas de remuneración

- **Administrativo:** remuneración = salario mensual.
- **Por hora:** remuneración = tarifa por hora × horas trabajadas.
- **Por comisión:** remuneración = ventas × porcentaje de comisión / 100.

## 5. Requisitos previos

Antes de ejecutar el proyecto se requiere:

1. JDK 21 instalado.
2. Apache NetBeans 22.
3. MongoDB Community Server en ejecución o una URI válida de MongoDB Atlas.
4. MongoDB Shell (`mongosh`) para inicializar datos de prueba, si se desea.
5. Conexión a Internet la primera vez que Maven descargue las dependencias.

## 6. Configuración de MongoDB

El proyecto incluye:

`src/main/resources/config.example.properties`

Configuración local predeterminada:

```properties
mongodb.uri=mongodb://localhost:27017
mongodb.database=sace_grupo6
```

Opciones de configuración:

### Opción A - Archivo local

Copiar `config.example.properties` como `config.properties` y modificar los valores necesarios.

### Opción B - Variables de entorno

- `SACE_MONGODB_URI`
- `SACE_MONGODB_DB`

Las variables de entorno tienen prioridad sobre el archivo de propiedades.

> No subir credenciales reales, cadenas privadas de MongoDB Atlas ni contraseñas al repositorio.

## 7. Inicialización de la base de datos

El proyecto incluye:

`mongo/init_sace.js`

Desde la raíz del proyecto, con MongoDB local iniciado:

### CMD / PowerShell

```bat
mongosh < mongo\init_sace.js
```

### Git Bash

```bash
mongosh < mongo/init_sace.js
```

El script crea índices únicos e inserta un usuario y seis empleados de prueba cuando la colección de empleados está vacía.

Base de datos predeterminada:

`sace_grupo6`

Colecciones utilizadas:

- `usuarios`
- `empleados`
- `remuneraciones`

## 8. Usuario de prueba

En una base nueva la aplicación puede crear automáticamente el siguiente usuario de prueba:

- **Usuario:** `admin`
- **Correo:** `admin@sace.local`
- **Contraseña:** `Admin123!`

La contraseña no se guarda en texto plano. Se almacena un `salt` y un `passwordHash` generado con PBKDF2-HMAC-SHA256.

## 9. Ejecución en NetBeans 22

1. Iniciar MongoDB.
2. Abrir NetBeans 22.
3. Seleccionar **File > Open Project**.
4. Seleccionar la carpeta raíz `SACE_Proyecto_Final_Grupo6`.
5. Esperar a que Maven descargue las dependencias.
6. Verificar que el proyecto utilice JDK 21.
7. Ejecutar la clase:

`src/main/java/sace/principal/Main.java`

También puede ejecutarse desde Maven:

```bash
mvn clean compile
mvn exec:java
```

## 10. Estructura del proyecto

```text
SACE_Proyecto_Final_Grupo6/
├── mongo/
│   ├── init_sace.js
│   └── README_MONGODB.md
├── docs/
│   ├── diagrama_clases.puml
│   ├── diagrama_jerarquico.mmd
│   └── flujograma_login_operaciones.mmd
├── src/main/java/sace/
│   ├── config/
│   ├── dao/
│   ├── modelo/
│   ├── principal/
│   ├── servicio/
│   ├── util/
│   └── vista/
├── src/main/resources/
│   └── config.example.properties
├── pom.xml
└── README.md
```

## 11. Arquitectura

### `modelo`
Contiene las entidades del dominio. `Empleado` es una clase abstracta y es la superclase de:

- `EmpleadoAdministrativo`
- `EmpleadoPorHora`
- `EmpleadoPorComision`

También incluye `Usuario` y `Remuneracion`.

### `dao`
Gestiona la lectura y escritura en MongoDB:

- `EmpleadoDAO`
- `UsuarioDAO`
- `RemuneracionDAO`

### `servicio`
Contiene reglas y validaciones de negocio:

- `EmpleadoService`
- `LoginService`
- `RemuneracionService`

### `vista`
Contiene la interfaz Java Swing:

- `LoginFrame`
- `MainFrame`
- `DashboardPanel`
- `EmpleadosPanel`
- `RemuneracionesPanel`
- `ReportesPanel`
- `HistorialPanel`
- `UI`

### `config`
Gestiona la configuración y conexión a MongoDB.

### `util`
Incluye utilidades, entre ellas la protección y verificación de contraseñas.

## 12. Validaciones implementadas

- Código de empleado obligatorio y único.
- Documento obligatorio y único.
- Documento con formato alfanumérico y guion de 5 a 25 caracteres.
- Nombre y cargo obligatorios.
- Fecha de ingreso obligatoria y no futura.
- Tipo de empleado válido.
- Salario mensual no negativo.
- Tarifa por hora no negativa.
- Horas trabajadas no negativas.
- Comisión entre 0 y 100.
- Ventas no negativas.
- No calcular remuneración para empleados inexistentes o inactivos.
- Período de remuneración con formato `AAAA-MM`.
- Validación de campos vacíos en Login.
- Mensaje genérico para credenciales incorrectas.

## 13. Datos de prueba

El script `mongo/init_sace.js` incluye seis empleados distribuidos entre los tres tipos de contratación. Estos registros permiten demostrar búsquedas, filtros, reportes y visualización durante la defensa.

## 14. Git y GitHub

El trabajo debe mantenerse en un repositorio compartido. Cada integrante debe utilizar su propia cuenta y realizar commits periódicos y significativos.

Ejemplos de mensajes adecuados:

```text
feat: agregar validación de empleados duplicados
feat: implementar historial de remuneraciones
ui: mejorar distribución del panel de empleados
fix: validar empleados inactivos antes del cálculo
docs: actualizar documentación de MongoDB
```

No concentrar todo el historial en una sola cuenta ni crear commits artificiales al final.

## 15. Problemas comunes

### MongoDB no disponible

Comprobar que el servicio esté iniciado y que la URI sea correcta.

Conexión local esperada:

`mongodb://localhost:27017`

### Maven no encuentra dependencias

En NetBeans utilizar **Reload Project** o ejecutar:

```bash
mvn clean compile
```

### Login no permite ingresar

- Confirmar que MongoDB está activo.
- Ejecutar `mongo/init_sace.js` o iniciar la aplicación sobre una base nueva para crear el usuario de prueba.
- Verificar el nombre de la base `sace_grupo6`.

### Puerto 27017 ocupado o inaccesible

Comprobar el servicio MongoDB y cualquier configuración personalizada de red.

## 16. Entrega académica

La carpeta de entrega debe incluir, como mínimo:

- Proyecto NetBeans/Maven completo.
- README.
- Plantilla de configuración sin credenciales reales.
- Script o respaldo restaurable de MongoDB.
- Datos de prueba.
- Documento técnico final.
- Evidencia y enlace del repositorio GitHub.

## 17. Seguridad

- No almacenar contraseñas en texto plano.
- No versionar `config.properties` si contiene información privada.
- No publicar credenciales de MongoDB Atlas.
- No utilizar información financiera o personal real para las pruebas académicas.

---

**SACE - Grupo 6 | Programación Orientada a Objetos | Ciclo II 2026**
