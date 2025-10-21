package grupo_02.mongoQuerys;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class PruebaMongo {

    public static void main(String[] args) {
        mediciones_repository medicion= new mediciones_repository();
        //System.out.println(medicion.insertOne("1", LocalDateTime.now(),32,20.1F,"humedad",1000,10,"coruña","españa","activo"));
        //System.out.println(medicion.insertOne("1", LocalDateTime.now(),12,10.1F,"humedad",1000,10,"belgrano","argentina","activo"));
        //System.out.println(medicion.insertOne("1", LocalDateTime.now(),41,22.1F,"humedad",1000,10,"villa gessel","argentina","inactivo"));
        //System.out.println(medicion.insertOne("1", LocalDateTime.now(),15,20.1F,"humedad",1000,10,"bernal","argentina","inactivo"));

        medicion.info_max_and_min_temperatures_humidity(LocalDateTime.of(2025,01,01,01,00),LocalDateTime.of(2025,11,01,00,00), new ArrayList<>(Arrays.asList("ciudad","pais")),new ArrayList<>(Arrays.asList("ciudad")),new ArrayList<>(Arrays.asList("bernal")));
        //medicion.info_average_humidity_temperatures(LocalDateTime.of(2025,01,01,01,00),LocalDateTime.of(2025,11,01,00,00),new ArrayList<>(Arrays.asList("ciudad","pais")),new ArrayList<>(Arrays.asList("ciudad")),new ArrayList<>(Arrays.asList("bernal")));
    }
}
