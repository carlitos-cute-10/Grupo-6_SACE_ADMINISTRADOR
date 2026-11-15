# Documento técnico — SACE Grupo 6 (Borrador para revisión)

## 1. Portada
Completar universidad, asignatura, docente, título, grupo de tutoría, grupo de proyecto, integrantes ordenados por apellido, usuario GitHub de cada integrante y fecha.

## 2. Índice
Actualizar al cerrar la versión final.

## 3. Introducción
SACE es una aplicación de escritorio para administrar empleados y registrar cálculos de remuneración. Se desarrolla con Java 21, Java Swing, POO y MongoDB, retomando el caso de estudio de la tarea de investigación del Grupo 6.

## 4. Enunciado del ejercicio
La empresa administra empleados administrativos, por hora y por comisión. El sistema registra y consulta empleados, permite modificar e inactivar registros, calcula remuneraciones según la modalidad, filtra resultados, genera reportes y conserva historial de cálculos.

## 5. Planteamiento del problema
La gestión manual o dispersa de empleados dificulta mantener datos consistentes y aplicar correctamente reglas diferentes de remuneración. Se requiere una solución persistente que centralice los registros y conserve evidencia histórica.

## 6. Análisis del problema
### Entradas
Código, nombre, documento, cargo, fecha de ingreso, tipo de empleado y datos salariales específicos.

### Procesos
Login, CRUD/inactivación, búsquedas, filtrado, cálculo polimórfico, registro histórico y reportes.

### Salidas
Listados de empleados, remuneración individual, resumen por tipo, total de remuneraciones e historial de cálculos.

### Reglas principales
- Código y documento únicos.
- Empleados inactivos no participan en nuevos cálculos.
- Administrativo: salario mensual.
- Por hora: tarifa × horas.
- Por comisión: ventas × porcentaje / 100.
- Comisión entre 0 y 100 y valores numéricos no negativos.

## 7. Diagrama jerárquico
Ver `diagrama_jerarquico.mmd`.

## 8. Flujograma
Ver `flujograma_login_operaciones.mmd`.

## 9. Diseño orientado a objetos
Ver `diagrama_clases.puml`. La clase abstracta Empleado define atributos comunes y `calcularRemuneracion()`. Las tres subclases implementan el cálculo correspondiente. Los DAO encapsulan MongoDB; los servicios contienen reglas de negocio y las vistas Swing gestionan la interacción.

## 10. Diseño de la base de datos
Colecciones: `usuarios`, `empleados` y `remuneraciones`. `usuarios` almacena salt + hash PBKDF2. `empleados` contiene los campos comunes y los específicos según `tipo`. `remuneraciones` conserva código, período, tipo, detalle, importe y fecha del cálculo.

## 11. Evidencias de implementación y pruebas
Pendiente de insertar capturas reales de Login, Dashboard, CRUD, cálculo, reportes, historial y MongoDB con mínimo seis registros.

## 12. Conclusiones y recomendaciones
La solución aplica encapsulamiento, herencia, sobrescritura, polimorfismo, separación de responsabilidades, persistencia y validaciones. Como mejoras futuras se pueden incorporar roles, exportación PDF/Excel y cálculos laborales adicionales.

## 13. Anexos
Agregar evidencia GitHub, tabla de pruebas, capturas y fragmentos de código relevantes.

## 14. Bibliografía
Completar en APA 7 con documentación oficial de Java, Swing, MongoDB y demás fuentes usadas.
