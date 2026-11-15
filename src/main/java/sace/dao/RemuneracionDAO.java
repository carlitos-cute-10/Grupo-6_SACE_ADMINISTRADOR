package sace.dao;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import sace.config.MongoConnection;
import sace.modelo.Remuneracion;
import java.time.LocalDateTime;
import java.util.*;

public class RemuneracionDAO {
    private MongoCollection<Document> col(){ return MongoConnection.db().getCollection("remuneraciones"); }
    public void insertar(Remuneracion r){ col().insertOne(new Document("empleadoCodigo",r.empleadoCodigo()).append("periodo",r.periodo()).append("tipoEmpleado",r.tipoEmpleado()).append("detalle",r.detalle()).append("importe",r.importe()).append("fechaCalculo",r.fechaCalculo().toString())); }
    public List<Remuneracion> listar(String codigo,String periodo){
        Document q=new Document(); if(codigo!=null&&!codigo.isBlank()) q.append("empleadoCodigo",codigo.trim().toUpperCase()); if(periodo!=null&&!periodo.isBlank()) q.append("periodo",periodo.trim());
        List<Remuneracion> r=new ArrayList<>(); for(Document d:col().find(q).sort(new Document("fechaCalculo",-1))) r.add(new Remuneracion(d.getString("empleadoCodigo"),d.getString("periodo"),d.getString("tipoEmpleado"),d.getString("detalle"),((Number)d.get("importe")).doubleValue(),LocalDateTime.parse(d.getString("fechaCalculo")))); return r;
    }
}
