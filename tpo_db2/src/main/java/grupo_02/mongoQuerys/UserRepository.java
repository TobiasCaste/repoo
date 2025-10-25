package grupo_02.mongoQuerys;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
import com.mongodb.client.result.UpdateResult;
import grupo_02.connectors.mongoConnector;
import grupo_02.entities.User;
import grupo_02.mapper.UserMapper;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;


@Repository
public class UserRepository {

    // Se cambió a `MongoCollection<Document>` para tipado correcto
    private final MongoCollection collection;

    // Se usa inyección por constructor. Spring buscará un bean 'mongoConnector' para inyectar.
    public UserRepository() {
        mongoConnector mongoDB=new mongoConnector();

        collection=mongoDB.get_collection("usuarios");
    }

    public User insertOne(User user) {

        try {
            Document userDoc = UserMapper.toDocument(user);

            // 1. Insertar el documento. No necesitamos almacenar el InsertOneResult
            collection.insertOne(userDoc);

            // 2. CORRECCIÓN CLAVE: Devolver la entidad User mapeando el Documento 'query'.
            // Se asume que UserMapper.toUser(Document) existe.
            return UserMapper.toUser(userDoc);
        }
        catch (Exception e) {
            System.out.println("error type: "+ e+" at insertOne method in usuarios.java");
            // En caso de error, es mejor lanzar una excepción para que el controlador o servicio lo maneje.
            throw new RuntimeException("Error al insertar el usuario en MongoDB", e);
        }

    }

    public Optional<User> findByEmail(String userEmail) {


        // Pipeline de agregación
        List<Bson> pipeline = Arrays.asList(

                Aggregates.match(Filters.eq("email",userEmail)),
                Aggregates.project(Projections.fields(
                        Projections.include("id_usuario", "name", "email", "password", "role", "isActive", "lastLogin", "createdAt", "updatedAt"),
                        Projections.excludeId()
                )),
                Aggregates.limit(1)
        );

        // Ejecutar la agregación
        Document doc = (Document) collection.aggregate(pipeline).first();

        return Optional.ofNullable(UserMapper.toUser(doc));
    }

    public Optional<User> findByIdUsuario(String idUsuario) {

        // Pipeline de agregación (mantenemos tu lógica)
        List<Bson> pipeline = Arrays.asList(

                Aggregates.match(Filters.eq("id_usuario", idUsuario)),
                Aggregates.project(Projections.fields(
                        // Incluye todos los campos que el mapper necesita, especialmente 'id_usuario' y fechas
                        Projections.include("id_usuario", "name", "email", "password", "role", "isActive", "lastLogin", "createdAt", "updatedAt"),
                        Projections.excludeId() // Mantienes esta exclusión si usas 'id_usuario' como clave
                )),
                Aggregates.limit(1)
        );


        Document doc = (Document) collection.aggregate(pipeline).first();

        if (doc == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(UserMapper.toUser(doc));
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