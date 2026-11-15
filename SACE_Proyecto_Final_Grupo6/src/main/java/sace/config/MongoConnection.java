package sace.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public final class MongoConnection {
    private static MongoClient client;
    private MongoConnection(){}
    public static synchronized MongoDatabase db(){
        if(client==null) client=MongoClients.create(MongoConfig.uri());
        MongoDatabase db=client.getDatabase(MongoConfig.database());
        db.runCommand(new Document("ping",1));
        return db;
    }
    public static synchronized void close(){ if(client!=null){ client.close(); client=null; } }
}
