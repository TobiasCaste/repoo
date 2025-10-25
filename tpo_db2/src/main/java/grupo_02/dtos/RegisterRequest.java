package grupo_02.dtos;

import com.mongodb.annotations.Sealed;
import lombok.*;

@Getter
@Setter
public class RegisterRequest {
    private String nombre;
    private String email;
    private String password;
}
