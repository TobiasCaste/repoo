package grupo_02.mapper;

import com.mongodb.Function;
import com.mongodb.client.result.InsertOneResult;
import grupo_02.entities.Role;
import grupo_02.entities.User;
import org.bson.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class UserMapper {

    public static User toUser(Document document) {
        if (document == null) {
            return null;
        }

        Function<String, LocalDateTime> toLocalDateTime = field -> {
            Date date = document.getDate(field);
            return (date != null)
                    ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                    : null;
        };


        Role role = null;
        String roleString = document.getString("role");
        if (roleString != null) {
            String roleKey = roleString.toUpperCase();

            if (roleKey.equals("USUARIO")) {
                roleKey = "USUARIO";
            }
            else if (roleKey.equals("ADMINISTRATOR")) {
                roleKey = "ADMINISTRADOR";
            }
            else if (roleKey.equals("    TECNICO\n")) {
                roleKey = "TECNICO";
            }

            try {
                role = Role.valueOf(roleKey);
            } catch (IllegalArgumentException e) {
                System.err.println("Error de mapeo: El rol '" + roleString + "' no es un valor válido de Role. Asignando null.");
            }
        }

        return new User(
                null, // Long: Conversión de String a Long ("id_usuario")
                document.getString("email"),
                document.getString("password"),
                document.getString("name"),
                role,
                document.getBoolean("isActive"),
                toLocalDateTime.apply("lastLogin"),
                toLocalDateTime.apply("createdAt"),
                toLocalDateTime.apply("updatedAt")
        );
    }

    public static Document toDocument(User user) {
        if (user == null) {
            return null;
        }

        Document document = new Document();

        document.append("email", user.getEmail());
        document.append("name", user.getName());
        document.append("password", user.getPassword());

        if (user.getRole() != null) {
            document.append("role", user.getRole().name());
        }

        document.append("isActive", user.getIsActive());

        ZoneId defaultZone = ZoneId.systemDefault();

        if (user.getLastLogin() != null) {
            document.append("lastLogin", Date.from(user.getLastLogin().atZone(defaultZone).toInstant()));
        }
        if (user.getCreatedAt() != null) {
            document.append("createdAt", Date.from(user.getCreatedAt().atZone(defaultZone).toInstant()));
        }
        if (user.getUpdatedAt() != null) {
            document.append("updatedAt", Date.from(user.getUpdatedAt().atZone(defaultZone).toInstant()));
        }

        return document;
    }
}
