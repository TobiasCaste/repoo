package grupo_02.service;

import grupo_02.dtos.LoginRequest;
import grupo_02.dtos.RegisterRequest;
import grupo_02.entities.Role;
import grupo_02.entities.User;
import grupo_02.mongoQuerys.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterRequest input) {
        User user = new User(null,
                input.getEmail(),
                passwordEncoder.encode(input.getPassword()),
                input.getNombre(),
                Role.USUARIO,
                Boolean.TRUE,
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now()

        );
        System.out.println(user.toString());
        return userRepository.insertOne(user);
    }

    public User authenticate(LoginRequest input) {
        User user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isEnabled()) {
            throw new RuntimeException("Account not verified. Please verify your account.");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return user;
    }
}
