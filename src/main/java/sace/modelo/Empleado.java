package sace.modelo;

import java.time.LocalDate;

/** Clase base de los tres tipos de empleado del sistema. */
public abstract class Empleado {
    private String codigo;
    private String nombre;
    private String documento;
    private String cargo;
    private LocalDate fechaIngreso;
    private boolean activo;

    protected Empleado(String codigo, String nombre, String documento, String cargo, LocalDate fechaIngreso, boolean activo) {
        setCodigo(codigo); setNombre(nombre); setDocumento(documento); setCargo(cargo); setFechaIngreso(fechaIngreso); this.activo = activo;
    }
    public abstract double calcularRemuneracion();
    public abstract String getTipoEmpleado();

    public String getCodigo(){ return codigo; }
    public final void setCodigo(String codigo){ if(codigo==null||codigo.isBlank()) throw new IllegalArgumentException("El código es obligatorio."); this.codigo=codigo.trim().toUpperCase(); }
    public String getNombre(){ return nombre; }
    public final void setNombre(String nombre){ if(nombre==null||!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) throw new IllegalArgumentException("El nombre es obligatorio y no puede contener ni números ni caracteres especiales."); this.nombre=nombre.trim(); }
    public String getDocumento(){ return documento; }
    public final void setDocumento(String documento){ if(documento==null||documento.isBlank()) throw new IllegalArgumentException("El documento es obligatorio."); this.documento=documento.trim(); }
    public String getCargo(){ return cargo; }
    public final void setCargo(String cargo){ if(cargo==null||!cargo.matches("^^[a-zA-Záé''ióúÁÉÍÓÚñÑ\\s]+$")) throw new IllegalArgumentException("El cargo es obligatorio y no puede contener números."); this.cargo = cargo.trim(); }
    public LocalDate getFechaIngreso(){ return fechaIngreso; }
    public final void setFechaIngreso(LocalDate fechaIngreso){ if(fechaIngreso==null) throw new IllegalArgumentException("La fecha de ingreso es obligatoria."); if(fechaIngreso.isAfter(LocalDate.now())) throw new IllegalArgumentException("La fecha de ingreso no puede estar en el futuro."); this.fechaIngreso=fechaIngreso; }
    public boolean isActivo(){ return activo; }
    public void setActivo(boolean activo){ this.activo=activo; }
}
