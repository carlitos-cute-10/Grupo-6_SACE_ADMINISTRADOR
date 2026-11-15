package sace.util;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class PasswordUtil {
    private static final int ITERACIONES=120000, BITS=256;
    private PasswordUtil(){}
    public static String nuevoSalt(){ byte[] b=new byte[16]; new SecureRandom().nextBytes(b); return Base64.getEncoder().encodeToString(b); }
    public static String hash(char[] password,String salt){
        try{
            PBEKeySpec spec=new PBEKeySpec(password,Base64.getDecoder().decode(salt),ITERACIONES,BITS);
            byte[] h=SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
            spec.clearPassword(); return Base64.getEncoder().encodeToString(h);
        }catch(Exception e){ throw new IllegalStateException("No fue posible proteger la contraseña.",e); }
    }
    public static boolean verificar(char[] password,String salt,String esperado){ return hash(password,salt).equals(esperado); }
}
