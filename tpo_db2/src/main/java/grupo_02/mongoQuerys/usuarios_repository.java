package grupo_02.mongoQuerys;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import grupo_02.connectors.mongoConnector;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDateTime;
import java.util.*;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class usuarios_repository {

    private MongoCollection collection;

    public usuarios_repository(){
        mongoConnector mongoDB=new mongoConnector();

        collection=mongoDB.get_collection("usuarios");
    }

    public String insertOne(String id_usuario, String name, String email, String password, LocalDateTime createdAt, String role) {

        try {
            Document query = new Document().append("id_usuario", id_usuario).append("name", name).append("email", email).append("password", password).append("createdAt", createdAt).append("role", role);

            InsertOneResult result = collection.insertOne(query);

            return result.getInsertedId().toString();
        }
        catch (Exception e) {
            System.out.println("error type: "+ e+" at insertOne method in usuarios.java");
            return "error";
        }

    }

    public Optional<String> findByEmail(
            String userEmail) {


        // Pipeline de agregación
        List<Bson> pipeline = Arrays.asList(

                Aggregates.match(Filters.eq("email",userEmail)),
                Aggregates.project(Projections.fields(
                        Projections.include("name","email","role","isActive"),
                        Projections.excludeId()
                )),
                Aggregates.limit(1)
        );

        // Ejecutar la agregación
        AggregateIterable<Document> resultados = collection.aggregate(pipeline);

        // Mostrar resultados
        for (Document doc : resultados) {
            System.out.println(doc.toJson());
            return Optional.of(doc.toJson());
        }

        return Optional.empty();
    }

    public Optional<String> findByIdUsuario(
            String idUsuario) {


        // Pipeline de agregación
        List<Bson> pipeline = Arrays.asList(

                Aggregates.match(Filters.eq("id_usuario",idUsuario)),
                Aggregates.project(Projections.fields(
                        Projections.include("name","email","role","isActive"),
                        Projections.excludeId()
                )),
                Aggregates.limit(1)
        );

        // Ejecutar la agregación
        AggregateIterable<Document> resultados = collection.aggregate(pipeline);

        // Mostrar resultados
        for (Document doc : resultados) {
            System.out.println(doc.toJson());
            return Optional.of(doc.toJson());
        }

        return Optional.empty();
    }

    public Optional<String> updateLastLogin(String idUsuario) {

        // FILTRO
        Bson filtro = Filters.eq("id_usuario", idUsuario);

        // DATOS A ACTUALIZAR
        Bson actualizacion = Updates.set("last_login", LocalDateTime.now());

        //UPDATE
        UpdateResult resultados = collection.updateOne(filtro,actualizacion);

        return Optional.of("ok");

    }

    public Optional<String> updatePassword(String idUsuario, String password){


        // FILTRO
        Bson filtro = Filters.eq("id_usuario",idUsuario);

        //ACtualizacion
        Bson actualizacion = Updates.set("password",password);

        //Update
        UpdateResult resultados = collection.updateOne(filtro,actualizacion);


        return Optional.of("ok");
    }

    public Optional<Boolean> hasRole(String idUsuario, String role){


        // PIPELINE
        List<Bson> pipeline = Arrays.asList(
            Filters.and(
                Filters.eq("id_usuario", idUsuario),
                Filters.eq("role",role)
            ),
            Aggregates.limit(1)
        );

        //Query
        AggregateIterable<Document> resultados = collection.aggregate(pipeline);

        return Optional.of(true);
    }

    public Optional<Boolean> isActive(String idUsuario){


        // PIPELINE
        List<Bson> pipeline = Arrays.asList(
                Filters.and(
                        Filters.eq("id_usuario", idUsuario),
                        Filters.eq("is_active",true)
                )
        );

        //Query
        AggregateIterable<Document> resultados = collection.aggregate(pipeline);


        return Optional.of(true);
    }
}