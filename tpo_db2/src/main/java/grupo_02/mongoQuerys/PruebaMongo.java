package grupo_02.mongoQuerys;

import java.time.LocalDateTime;

public class PruebaMongo {

    public static void main(String[] args) {
        mediciones_repository medicion= new mediciones_repository();
        //System.out.println(medicion.insertOne("1", LocalDateTime.now(),40,30.1F,"humedad",1000,10,"coruña","españa","activo"));
        //System.out.println(medicion.insertOne("1", LocalDateTime.now(),10,20.1F,"humedad",1000,10,"belgrano","argentina","activo"));
        //System.out.println(medicion.insertOne("1", LocalDateTime.now(),46,42.1F,"humedad",1000,10,"villa gessel","argentina","inactivo"));
        //System.out.println(medicion.insertOne("1", LocalDateTime.now(),17,70.1F,"humedad",1000,10,"bernal","argentina","inactivo"));
        medicion.info_max_and_min_temperatures_humidity(LocalDateTime.of(2025,01,01,01,00),LocalDateTime.of(2025,11,01,00,00),"ciudad");
        medicion.info_average_humidity_temperatures(LocalDateTime.of(2025,01,01,01,00),LocalDateTime.of(2025,11,01,00,00),"ciudad");
    }
}
