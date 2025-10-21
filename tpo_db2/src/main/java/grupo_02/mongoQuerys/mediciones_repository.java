package grupo_02.mongoQuerys;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.InsertOneResult;
import grupo_02.connectors.mongoConnector;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class mediciones_repository {

        private MongoCollection collection;


        public mediciones_repository(){
            mongoConnector mongoDB=new mongoConnector();

            collection=mongoDB.get_collection("mediciones");
        }

        public String insertOne(String id_sensor, LocalDateTime time,int temperatura,Float humedad,String tipo_sensor,int latitud, int longitud, String ciudad, String pais, String estado) {

            try {
                Document query = new Document().append("id_sensor", id_sensor).append("fecha_hora", time).append("temperatura", temperatura).append("humedad", humedad).append("tipo_sensor", tipo_sensor).append("latitud", latitud).append("longitud", longitud).append("ciudad", ciudad).append("pais", pais).append("estado", estado);

                InsertOneResult result = collection.insertOne(query);

                return result.getInsertedId().toString();
            }
            catch (Exception e) {
                System.out.println("error type: "+ e+" at insertOne method in mediciones.java");
                return "error";
            }

        }

    public String info_max_and_min_temperatures_humidity(
            LocalDateTime from,
            LocalDateTime to,
            ArrayList<String> groupFields,
            ArrayList<String> filterFields,
            ArrayList<String> filterValues) {

        // Conversión de LocalDateTime a Date (MongoDB usa Date)
        Date fromDate = Date.from(from.atZone(ZoneId.systemDefault()).toInstant());
        Date toDate = Date.from(to.atZone(ZoneId.systemDefault()).toInstant());

        // Construir los campos de agrupación (_id)
        Document groupId = new Document();
        for (String field : groupFields) {
            groupId.append(field, "$" + field);
        }

        // Construir condiciones del match
        List<Bson> condiciones = new ArrayList<>();
        condiciones.add(Filters.gte("fecha_hora", fromDate));
        condiciones.add(Filters.lte("fecha_hora", toDate));

        // Agregar filtros dinámicos
        for (int i = 0; i < filterFields.size(); i++) {
            condiciones.add(Filters.eq(filterFields.get(i), filterValues.get(i)));
        }

        // Pipeline de agregación
        List<Bson> pipeline = Arrays.asList(
                Aggregates.match(Filters.and(condiciones)),
                Aggregates.group(
                        groupId,
                        Accumulators.max("maxima_humedad", "$humedad"),
                        Accumulators.min("minima_humedad", "$humedad"),
                        Accumulators.max("temperatura_maxima", "$temperatura"),
                        Accumulators.min("temperatura_minima", "$temperatura")
                )
        );

        // Ejecutar la agregación
        AggregateIterable<Document> resultados = collection.aggregate(pipeline);

        for (Document doc : resultados) {
            System.out.println(doc.toJson());
        }

        return "OK";
    }


    public String info_average_humidity_temperatures(LocalDateTime from , LocalDateTime to, ArrayList<String> types){

        // Conversión de LocalDateTime a Date (MongoDB usa Date)
        Date fromDate = Date.from(from.atZone(ZoneId.systemDefault()).toInstant());
        Date toDate = Date.from(to.atZone(ZoneId.systemDefault()).toInstant());

        Document fields=new Document();
        for (String i: types){
            fields.append(i, "$" + i);
        }


        // Pipeline de agregación
        List<Bson> pipeline = Arrays.asList(

                Aggregates.match(Filters.and(
                        Filters.gte("fecha_hora", fromDate), // ← mismo campo que en tu consulta Mongo
                        Filters.lte("fecha_hora", toDate)
                )),
                Aggregates.group(
                        new Document("_id",fields),  // agrupa según "pais", "ciudad", etc.
                        Accumulators.avg("humedad_promedio", "$humedad"),
                        Accumulators.avg("temperatura_promedio", "$temperatura")
                )

        );

        // Ejecutar la agregación
        AggregateIterable<Document> resultados = collection.aggregate(pipeline);

        // Mostrar resultados
        for (Document doc : resultados) {
            System.out.println(doc.toJson());
        }


        return "ok";
    }




}
