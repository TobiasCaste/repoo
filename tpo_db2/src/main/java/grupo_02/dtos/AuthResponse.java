package grupo_02.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AuthResponse {
    private String token;
    private long expiresIn;

    public AuthResponse(String token, long expiresIn){
        this.token=token;
        this.expiresIn=expiresIn;
    }
}
