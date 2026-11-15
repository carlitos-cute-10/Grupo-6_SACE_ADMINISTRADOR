package sace.servicio;

import sace.dao.EmpleadoDAO;
import sace.modelo.*;
import java.time.LocalDate;
import java.util.List;

public class EmpleadoService {
    private final EmpleadoDAO dao=new EmpleadoDAO();
    public void registrar(String codigo,String nombre,String documento,String cargo,LocalDate fecha,String tipo,double v1,double v2){
        validarTexto(codigo,"Código"); validarTexto(nombre,"Nombre"); validarTexto(documento,"Documento"); validarTexto(cargo,"Cargo");
        if(!documento.trim().matches("[A-Za-z0-9-]{5,25}")) throw new IllegalArgumentException("El documento solo puede contener letras, números y guion (5 a 25 caracteres).");
        if(dao.existeCodigo(codigo)) throw new IllegalArgumentException("Ya existe un empleado con ese código.");
        if(dao.existeDocumento(documento,null)) throw new IllegalArgumentException("Ya existe un empleado con ese documento.");
        dao.insertar(crear(codigo,nombre,documento,cargo,fecha,tipo,true,v1,v2));
    }
    public void actualizar(String codigo,String nombre,String documento,String cargo,LocalDate fecha,String tipo,double v1,double v2){
        if(dao.buscarPorCodigo(codigo)==null) throw new IllegalArgumentException("El empleado no existe.");
        validarTexto(nombre,"Nombre"); validarTexto(documento,"Documento"); validarTexto(cargo,"Cargo");
        if(!documento.trim().matches("[A-Za-z0-9-]{5,25}")) throw new IllegalArgumentException("El documento solo puede contener letras, números y guion (5 a 25 caracteres).");
        if(dao.existeDocumento(documento,codigo.toUpperCase())) throw new IllegalArgumentException("El documento ya pertenece a otro empleado.");
        Empleado actual=dao.buscarPorCodigo(codigo); dao.actualizar(crear(codigo,nombre,documento,cargo,fecha,tipo,actual.isActivo(),v1,v2));
    }
    public void inactivar(String codigo){ Empleado e=dao.buscarPorCodigo(codigo); if(e==null) throw new IllegalArgumentException("El empleado no existe."); if(!e.isActivo()) throw new IllegalArgumentException("El empleado ya está inactivo."); dao.inactivar(codigo); }
    public Empleado buscarCodigo(String c){ return dao.buscarPorCodigo(c); }
    public List<Empleado> listar(){ return dao.listarTodos(); }
    public List<Empleado> buscar(String t){ return t==null||t.isBlank()?listar():dao.buscarTexto(t); }
    public List<Empleado> porTipo(String t){ return t==null||t.equals("Todos")?listar():dao.listarPorTipo(t); }
    public long activos(){ return dao.contarActivos(); }
    public long contarTipo(String t){ return dao.contarPorTipo(t); }
    public double nominaActual(){ return listar().stream().filter(Empleado::isActivo).mapToDouble(Empleado::calcularRemuneracion).sum(); }
    private Empleado crear(String c,String n,String d,String cargo,LocalDate f,String tipo,boolean act,double v1,double v2){
        return switch(tipo){
            case "Administrativo" -> new EmpleadoAdministrativo(c,n,d,cargo,f,act,v1);
            case "Por hora" -> new EmpleadoPorHora(c,n,d,cargo,f,act,v1,v2);
            case "Por comisión" -> new EmpleadoPorComision(c,n,d,cargo,f,act,v1,v2);
            default -> throw new IllegalArgumentException("Seleccione un tipo de empleado válido.");
        };
    }
    private void validarTexto(String v,String n){ if(v==null||v.isBlank()) throw new IllegalArgumentException(n+" es obligatorio."); }
}
