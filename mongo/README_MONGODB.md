# Inicialización MongoDB

La aplicación utiliza la base `sace_grupo6` por defecto.

## Opción A - automática
Inicie MongoDB y ejecute la aplicación. Si la colección `usuarios` está vacía, SACE crea el usuario `admin`.

## Opción B - datos de defensa
Ejecute:

```bash
mongosh < mongo/init_sace.js
```

Esto crea índices únicos y seis empleados de prueba.

Usuario de prueba: `admin` / `Admin123!`. En MongoDB solo quedan almacenados salt y hash PBKDF2.
