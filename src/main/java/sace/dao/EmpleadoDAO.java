package sace.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;
import sace.config.MongoConnection;
import sace.modelo.*;
import java.time.LocalDate;
import java.util.*;

public class EmpleadoDAO {
    private MongoCollection<Document> col(){
        MongoCollection<Document> c=MongoConnection.db().getCollection("empleados");
        try{ c.createIndex(new Document("codigo",1),new IndexOptions().unique(true)); c.createIndex(new Document("documento",1),new IndexOptions().unique(true)); }catch(Exception ignored){}
        return c;
    }
    public boolean existeCodigo(String codigo){ return col().countDocuments(new Document("codigo",codigo.trim().toUpperCase()))>0; }
    public boolean existeDocumento(String doc,String codigoExcluir){
        Document q=new Document("documento",doc.trim()); if(codigoExcluir!=null) q.append("codigo",new Document("$ne",codigoExcluir));
        return col().countDocuments(q)>0;
    }
    public void insertar(Empleado e){ col().insertOne(toDoc(e)); }
    public void actualizar(Empleado e){ col().replaceOne(new Document("codigo",e.getCodigo()),toDoc(e)); }
    public void inactivar(String codigo){ col().updateOne(new Document("codigo",codigo),new Document("$set",new Document("activo",false))); }
    public Empleado buscarPorCodigo(String codigo){ Document d=col().find(new Document("codigo",codigo.trim().toUpperCase())).first(); return d==null?null:fromDoc(d); }
    public List<Empleado> listarTodos(){ List<Empleado> r=new ArrayList<>(); for(Document d:col().find().sort(new Document("nombre",1))) r.add(fromDoc(d)); return r; }
    public List<Empleado> buscarTexto(String texto){
        String rx=".*"+java.util.regex.Pattern.quote(texto.trim())+".*";
        Document q=new Document("$or",List.of(new Document("codigo",new Document("$regex",rx).append("$options","i")),new Document("nombre",new Document("$regex",rx).append("$options","i")),new Document("documento",new Document("$regex",rx).append("$options","i"))));
        List<Empleado> r=new ArrayList<>(); for(Document d:col().find(q).sort(new Document("nombre",1))) r.add(fromDoc(d)); return r;
    }
    public List<Empleado> listarPorTipo(String tipo){ List<Empleado> r=new ArrayList<>(); for(Document d:col().find(new Document("tipo",tipo)).sort(new Document("nombre",1))) r.add(fromDoc(d)); return r; }
    public long contarActivos(){ return col().countDocuments(new Document("activo",true)); }
    public long contarPorTipo(String tipo){ return col().countDocuments(new Document("tipo",tipo).append("activo",true)); }

    private Document toDoc(Empleado e){
        Document d=new Document("codigo",e.getCodigo()).append("nombre",e.getNombre()).append("documento",e.getDocumento()).append("cargo",e.getCargo())
                .append("fechaIngreso",e.getFechaIngreso().toString()).append("tipo",e.getTipoEmpleado()).append("activo",e.isActivo());
        if(e instanceof EmpleadoAdministrativo a) d.append("salarioMensual",a.getSalarioMensual());
        else if(e instanceof EmpleadoPorHora h) d.append("tarifaHora",h.getTarifaHora()).append("horasTrabajadas",h.getHorasTrabajadas());
        else if(e instanceof EmpleadoPorComision c) d.append("porcentajeComision",c.getPorcentajeComision()).append("ventas",c.getVentas());
        return d;
    }
    private Empleado fromDoc(Document d){
        String c=d.getString("codigo"),n=d.getString("nombre"),doc=d.getString("documento"),cargo=d.getString("cargo"),tipo=d.getString("tipo");
        LocalDate f=LocalDate.parse(d.getString("fechaIngreso")); boolean act=d.getBoolean("activo",true);
        if("Administrativo".equals(tipo)) return new EmpleadoAdministrativo(c,n,doc,cargo,f,act,num(d,"salarioMensual"));
        if("Por hora".equals(tipo)) return new EmpleadoPorHora(c,n,doc,cargo,f,act,num(d,"tarifaHora"),num(d,"horasTrabajadas"));
        return new EmpleadoPorComision(c,n,doc,cargo,f,act,num(d,"porcentajeComision"),num(d,"ventas"));
    }
    private double num(Document d,String k){ Number n=(Number)d.get(k); return n==null?0:n.doubleValue(); }
}
