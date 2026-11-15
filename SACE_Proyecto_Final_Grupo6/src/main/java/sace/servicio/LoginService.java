package sace.servicio;

import sace.dao.UsuarioDAO;
import sace.modelo.Usuario;

public class LoginService {
    private final UsuarioDAO dao=new UsuarioDAO();
    public void preparar(){ dao.asegurarUsuarioPrueba(); }
    public Usuario login(String id,char[] clave){ if(id==null||id.isBlank()||clave==null||clave.length==0) throw new IllegalArgumentException("Ingrese usuario/correo y contraseña."); return dao.autenticar(id.trim(),clave); }
}
