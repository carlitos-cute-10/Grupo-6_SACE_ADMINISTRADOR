package sace.config;

import java.io.InputStream;
import java.util.Properties;

public final class MongoConfig {
    private MongoConfig(){}
    public static String uri(){ return valor("SACE_MONGODB_URI","mongodb.uri","mongodb://localhost:27017"); }
    public static String database(){ return valor("SACE_MONGODB_DB","mongodb.database","sace_grupo6"); }
    private static String valor(String env,String key,String def){
        String e=System.getenv(env); if(e!=null&&!e.isBlank()) return e;
        Properties p=new Properties();
        try(InputStream in=MongoConfig.class.getClassLoader().getResourceAsStream("config.properties")) { if(in!=null) p.load(in); } catch(Exception ignored){}
        if(p.isEmpty()) try(InputStream in=MongoConfig.class.getClassLoader().getResourceAsStream("config.example.properties")) { if(in!=null) p.load(in); } catch(Exception ignored){}
        return p.getProperty(key,def).trim();
    }
}
