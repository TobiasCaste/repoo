package grupo_02.connectors;

import com.mongodb.MongoClientException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.Collection;

public class mongoConnector {

    private MongoClient mongoClient;
    private MongoDatabase db;

    public mongoConnector(){

        String url = "mongodb://127.0.01:27017";
        try{
            mongoClient= MongoClients.create(url);
            db = mongoClient.getDatabase("tpo");
            //System.out.println("conectado");
            //Creamos una coleccion si no existe,
            //MongoCollection<Document> collection = db.getCollection("mediciones");
            //Document document=new Document().append("id_medicion",1).append("id_sensor",1).append("fecha_hora", LocalDateTime.now()).append("temperatura",23).append("humeda",16.7).append("tipo_sensor","humedad").append("latitud",1000).append("longitud",100).append("ciudad","quilmes").append("pais","argentina").append("estado","activo");
            //collection.insertOne(document);
            /*
            //Insertamos un documento
            Document data = new Document().append("name", "Juan").append("age", 22);
            InsertOneResult insertOneResult = collection.insertOne(data);
            System.out.println("\n--- insert one result ---");
            System.out.println(insertOneResult.getInsertedId());

            //Insertamos otro
            data = new Document().append("name", "Pedro").append("lastname", "Almodovar").append("age", 68);
            insertOneResult = collection.insertOne(data);
            System.out.println("\n--- insert one result ---");
            System.out.println(insertOneResult.getInsertedId());

            //Insertar varios al mismo tiempo
            List<Document> severalData = new ArrayList<Document>();
            severalData.add(new Document().append("name", "Elena").append("age", 33));
            severalData.add(new Document().append("name", "Ana").append("lastname", "Bolena").append("age", 35));
            InsertManyResult insertManyResult = collection.insertMany(severalData);
            System.out.println("\n--- insert many result ---");
            System.out.println(insertManyResult.getInsertedIds());

            // Consultar con un filtro
            Bson filter = Filters.eq("name", "Elena");
            FindIterable<Document> elementsFound = collection.find(filter);
            System.out.println("\n--- find(filter) result ---");
            for (Document document : elementsFound)
                System.out.println(document);


            // Consultar toda la colección
            FindIterable<Document> allCollection = collection.find();
            System.out.println("\n--- find() result ---");
            for (Document document : allCollection)
                System.out.println(document);


            // Modificar un elemento de la colección
            Bson filter = Filters.eq("name", "Ana");
            Bson newAge = Updates.set("age", 25);
            UpdateResult updateOne = collection.updateOne(filter, newAge);
            FindIterable<Document> elementsFound = collection.find(filter);
            System.out.println("\n--- updateOne() result ---");
            System.out.println(updateOne.getMatchedCount());
            for (Document document : elementsFound)
                System.out.println(document);


            // Borrar un elemento
            Bson filter = Filters.eq("name", "Pedro");
            DeleteResult deleteResult = collection.deleteMany(filter);
            System.out.println("\n--- Number of deleted elements with filter ---");
            System.out.println(deleteResult.getDeletedCount());
             */
            // Borrar todo
                /*
                DeleteResult deleteResult = collection.deleteMany(Filters.empty());
                System.out.println("\n--- Number of deleted elements with empty filter ---");
                System.out.println(deleteResult.getDeletedCount());
                */


        }
        catch (MongoClientException e) {
            System.out.println(e.getMessage());

        }

    }

    public MongoCollection get_collection(String name){
        try{
            return db.getCollection(name);

        }
        catch (Exception e){
            return null;
        }
    }
}
