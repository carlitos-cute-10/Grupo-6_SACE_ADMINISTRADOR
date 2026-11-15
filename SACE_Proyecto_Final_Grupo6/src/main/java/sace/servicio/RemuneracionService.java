package sace.servicio;

import sace.dao.*;
import sace.modelo.*;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

public class RemuneracionService {
    private final EmpleadoDAO empleados=new EmpleadoDAO(); private final RemuneracionDAO dao=new RemuneracionDAO();
    public Remuneracion calcularYGuardar(String codigo,String periodo){
        Empleado e=empleados.buscarPorCodigo(codigo); if(e==null) throw new IllegalArgumentException("Empleado inexistente."); if(!e.isActivo()) throw new IllegalArgumentException("No se puede calcular la remuneración de un empleado inactivo.");
        try{ YearMonth.parse(periodo); }catch(Exception ex){ throw new IllegalArgumentException("El período debe tener formato AAAA-MM."); }
        double importe=e.calcularRemuneracion(); String detalle=detalle(e);
        Remuneracion r=new Remuneracion(e.getCodigo(),periodo,e.getTipoEmpleado(),detalle,importe,LocalDateTime.now()); dao.insertar(r); return r;
    }
    public List<Remuneracion> historial(String codigo,String periodo){ return dao.listar(codigo,periodo); }
    private String detalle(Empleado e){
        if(e instanceof EmpleadoAdministrativo a) return String.format("Salario mensual: $%.2f",a.getSalarioMensual());
        if(e instanceof EmpleadoPorHora h) return String.format("%.2f horas × $%.2f",h.getHorasTrabajadas(),h.getTarifaHora());
        EmpleadoPorComision c=(EmpleadoPorComision)e; return String.format("Ventas $%.2f × %.2f%%",c.getVentas(),c.getPorcentajeComision());
    }
}
