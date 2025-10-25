package grupo_02.controllers;

import grupo_02.dtos.AuthResponse;
import grupo_02.dtos.LoginRequest;
import grupo_02.dtos.RegisterRequest;
import grupo_02.entities.User;
import grupo_02.service.AuthenticationService;
import grupo_02.service.JwtServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtServiceImpl jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtServiceImpl jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {
        User registeredUser = authenticationService.signup(registerRequest);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody LoginRequest loginRequest){
        User authenticatedUser = authenticationService.authenticate(loginRequest);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        AuthResponse authResponse = new AuthResponse(jwtToken, jwtService.getExpirationTime());
        return ResponseEntity.ok(authResponse);
    }
}
