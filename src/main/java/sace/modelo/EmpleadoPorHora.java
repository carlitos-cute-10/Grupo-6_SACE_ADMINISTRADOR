package sace.modelo;

import java.time.LocalDate;

public class EmpleadoPorHora extends Empleado {
    private double tarifaHora;
    private double horasTrabajadas;
    public EmpleadoPorHora(String codigo,String nombre,String documento,String cargo,LocalDate fechaIngreso,boolean activo,double tarifaHora,double horasTrabajadas){
        super(codigo,nombre,documento,cargo,fechaIngreso,activo); setTarifaHora(tarifaHora); setHorasTrabajadas(horasTrabajadas);
    }
    @Override public double calcularRemuneracion(){ return tarifaHora * horasTrabajadas; }
    @Override public String getTipoEmpleado(){ return "Por hora"; }
    public double getTarifaHora(){ return tarifaHora; }
    public final void setTarifaHora(double v){ if(v<=0) throw new IllegalArgumentException("La tarifa por hora debe ser mayor a cero."); tarifaHora=v; }
    public double getHorasTrabajadas(){ return horasTrabajadas; }
    public final void setHorasTrabajadas(double v){ if(v<0) throw new IllegalArgumentException("Las horas no pueden ser negativas."); horasTrabajadas=v; }
}
