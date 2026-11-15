// Inicialización de SACE Grupo 6. Ejecutar con mongosh: mongosh < init_sace.js
use sace_grupo6;

db.usuarios.createIndex({usuario:1},{unique:true});
db.usuarios.createIndex({correo:1},{unique:true});
db.empleados.createIndex({codigo:1},{unique:true});
db.empleados.createIndex({documento:1},{unique:true});

if (db.usuarios.countDocuments({usuario:"admin"}) === 0) {
  db.usuarios.insertOne({usuario:"admin", correo:"admin@sace.local", salt:"U0FDRUdydXBvNlNhbHQhIQ==", passwordHash:"sKzyIs1gUO3h76TTaPWnVUs4asKar7wx8Ztz2tBrPtU=", activo:true});
}

if (db.empleados.countDocuments({}) === 0) {
  db.empleados.insertMany([
    {codigo:"EMP-001",nombre:"Ana Martínez",documento:"06000001-1",cargo:"Administradora",fechaIngreso:"2024-01-15",tipo:"Administrativo",activo:true,salarioMensual:650.0},
    {codigo:"EMP-002",nombre:"Carlos López",documento:"06000002-2",cargo:"Técnico",fechaIngreso:"2024-02-01",tipo:"Por hora",activo:true,tarifaHora:4.5,horasTrabajadas:160.0},
    {codigo:"EMP-003",nombre:"Andrea Gómez",documento:"06000003-3",cargo:"Ejecutiva de ventas",fechaIngreso:"2024-03-10",tipo:"Por comisión",activo:true,porcentajeComision:5.0,ventas:5000.0},
    {codigo:"EMP-004",nombre:"Daniel Hernández",documento:"06000004-4",cargo:"Supervisor",fechaIngreso:"2023-11-05",tipo:"Administrativo",activo:true,salarioMensual:825.0},
    {codigo:"EMP-005",nombre:"María Rivera",documento:"06000005-5",cargo:"Auxiliar",fechaIngreso:"2025-01-20",tipo:"Por hora",activo:true,tarifaHora:5.25,horasTrabajadas:150.0},
    {codigo:"EMP-006",nombre:"José Ramírez",documento:"06000006-6",cargo:"Vendedor",fechaIngreso:"2025-02-14",tipo:"Por comisión",activo:true,porcentajeComision:6.0,ventas:7200.0}
  ]);
}
print("SACE inicializado. Usuario de prueba: admin");
