package rugal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class TestMongoDBConnexion {

	private static MongoClient mongoClient;
	
	public static MongoClient mongoClient() {
	    List<ServerAddress> saList = new ArrayList<>();
	    saList.add(new ServerAddress("cluster0-shard-00-00-4glbq.mongodb.net", 27017));
	    saList.add(new ServerAddress("cluster0-shard-00-01-4glbq.mongodb.net", 27017));
	    saList.add(new ServerAddress("cluster0-shard-00-02-4glbq.mongodb.net", 27017));

	    char[] pwd =  "solinum_123".toCharArray();
	    MongoCredential credential = MongoCredential.createCredential("solinum_123", "admin", pwd);

	    //set sslEnabled to true here
	    MongoClientOptions options = MongoClientOptions.builder()
	            .readPreference(ReadPreference.primaryPreferred())
	            .retryWrites(true)
	            .requiredReplicaSetName("Cluster0-shard-0")
	            .maxConnectionIdleTime(6000)
	            .sslEnabled(true)
	            .build();

	    
		MongoClient mongoClient = new MongoClient(saList, Arrays.asList(credential),options);     
	    return mongoClient;
	}

	public static void main(String[] args) {
		
		mongoClient = mongoClient();
		MongoDatabase database = mongoClient.getDatabase("taskmanager");
		
		//crete Connection
//        Mongo mongoClient = new Mongo("ind-asingh", 27017);//or "localhost",27017
        System.out.println("mongoClient :" + mongoClient);
       // DB db = mongoClient.getDB("collaborateur");//e.g. MYDB
        System.out.println("db :" + database);

//---------------------------Access your Collection-----------------------------
        MongoCollection<Document>  collection = database.getCollection("collaborateur");
        System.out.println("collection :" + collection);
        System.out.println("Count:---- " + collection.count());
        
        FindIterable<Document> results  = collection.find();
        for (Document doc : results) {
            System.out.print(doc.getString("name") + " : ");
            System.out.println(doc.getString("lastname"));
        }
        
     
	}
}
