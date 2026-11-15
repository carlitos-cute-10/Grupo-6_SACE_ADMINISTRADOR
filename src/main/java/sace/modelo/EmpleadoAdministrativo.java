package sace.modelo;

import java.time.LocalDate;

public class EmpleadoAdministrativo extends Empleado {
    private double salarioMensual;
    public EmpleadoAdministrativo(String codigo,String nombre,String documento,String cargo,LocalDate fechaIngreso,boolean activo,double salarioMensual){
        super(codigo,nombre,documento,cargo,fechaIngreso,activo); setSalarioMensual(salarioMensual);
    }
    @Override public double calcularRemuneracion(){ return salarioMensual; }
    @Override public String getTipoEmpleado(){ return "Administrativo"; }
    public double getSalarioMensual(){ return salarioMensual; }
    public final void setSalarioMensual(double v){ if(v<=0) throw new IllegalArgumentException("El salario mensual debe ser mayor a cero."); salarioMensual=v; }
}
