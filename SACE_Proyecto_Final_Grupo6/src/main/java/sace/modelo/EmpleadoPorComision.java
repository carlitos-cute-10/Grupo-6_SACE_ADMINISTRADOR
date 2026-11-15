package sace.modelo;

import java.time.LocalDate;

public class EmpleadoPorComision extends Empleado {
    private double porcentajeComision;
    private double ventas;
    public EmpleadoPorComision(String codigo,String nombre,String documento,String cargo,LocalDate fechaIngreso,boolean activo,double porcentajeComision,double ventas){
        super(codigo,nombre,documento,cargo,fechaIngreso,activo); setPorcentajeComision(porcentajeComision); setVentas(ventas);
    }
    @Override public double calcularRemuneracion(){ return ventas * porcentajeComision / 100.0; }
    @Override public String getTipoEmpleado(){ return "Por comisión"; }
    public double getPorcentajeComision(){ return porcentajeComision; }
    public final void setPorcentajeComision(double v){ if(v<0||v>100) throw new IllegalArgumentException("La comisión debe estar entre 0 y 100."); porcentajeComision=v; }
    public double getVentas(){ return ventas; }
    public final void setVentas(double v){ if(v<0) throw new IllegalArgumentException("Las ventas no pueden ser negativas."); ventas=v; }
}
