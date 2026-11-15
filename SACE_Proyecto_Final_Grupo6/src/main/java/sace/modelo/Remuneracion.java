package sace.modelo;

import java.time.LocalDateTime;

public record Remuneracion(String empleadoCodigo, String periodo, String tipoEmpleado, String detalle, double importe, LocalDateTime fechaCalculo) { }
