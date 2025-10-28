package grupo_02.service;

import grupo_02.connectors.PoolRedis;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class TokenService {

    public void saveToken(String email, String token, long expirationMillis) {
        try(Jedis jedis = PoolRedis.getInstancia().getConection()){
            jedis.setex(email, (int) (expirationMillis)/ 1000, token);
        } catch (Exception e){
            System.out.println("Error al querer guardar el token en redis: " + e.getMessage());
        }
    }
    public String getToken(String email){
        try(Jedis jedis = PoolRedis.getInstancia().getConection()){
            return jedis.get(email);
        } catch (Exception e){
            System.out.println("Error al querer obtener el token en redis: " + e.getMessage());
            return null;
        }
    }
    public void deleteToken(String email){
        try(Jedis jedis = PoolRedis.getInstancia().getConection()){
            jedis.del(email);
        } catch (Exception e){
            System.out.println("Error al querer eliminar el token en redis: " + e.getMessage());
        }
    }

}
