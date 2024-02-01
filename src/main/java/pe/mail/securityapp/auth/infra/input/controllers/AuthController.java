package pe.mail.securityapp.auth.infra.input.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.mail.securityapp.auth.app.login.LoginResponse;
import pe.mail.securityapp.auth.app.login.LoginRequest;
import pe.mail.securityapp.auth.app.register.RegisterRequest;
import pe.mail.securityapp.auth.app.register.RegisterResponse;
import pe.mail.securityapp.auth.app.services.IAuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final IAuthService authService;

  @PostMapping(value = "/login")
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest user) {
    return ResponseEntity.ok(authService.authenticate(user));
  }

  @PostMapping("/register")
  public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest user) {
    return ResponseEntity.status(201).body(authService.register(user));
  }

}
