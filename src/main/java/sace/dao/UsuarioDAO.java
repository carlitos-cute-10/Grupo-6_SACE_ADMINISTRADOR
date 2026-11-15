package sace.dao;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import sace.config.MongoConnection;
import sace.modelo.Usuario;
import sace.util.PasswordUtil;

public class UsuarioDAO {
    private MongoCollection<Document> col(){ return MongoConnection.db().getCollection("usuarios"); }
    public Usuario autenticar(String identificador,char[] clave){
        Document filtro=new Document("$or", java.util.List.of(new Document("usuario",identificador),new Document("correo",identificador)));
        Document d=col().find(filtro).first();
        if(d==null || !d.getBoolean("activo",true)) return null;
        String salt=d.getString("salt"), hash=d.getString("passwordHash");
        if(salt==null||hash==null||!PasswordUtil.verificar(clave,salt,hash)) return null;
        return new Usuario(d.getString("usuario"),d.getString("correo"),true);
    }
    public void asegurarUsuarioPrueba(){
        if(col().countDocuments()==0){
            String salt=PasswordUtil.nuevoSalt();
            col().insertOne(new Document("usuario","admin").append("correo","admin@sace.local")
                    .append("salt",salt).append("passwordHash",PasswordUtil.hash("Admin123!".toCharArray(),salt)).append("activo",true));
        }
    }
}
