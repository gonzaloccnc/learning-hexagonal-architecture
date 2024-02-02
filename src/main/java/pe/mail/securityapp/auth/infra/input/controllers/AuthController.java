package pe.mail.securityapp.auth.infra.input.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.mail.securityapp.auth.app.login.AuthenticateUseCase;
import pe.mail.securityapp.auth.app.login.LoginResponse;
import pe.mail.securityapp.auth.app.login.LoginRequest;
import pe.mail.securityapp.auth.app.register.RegisterRequest;
import pe.mail.securityapp.auth.app.register.RegisterResponse;
import pe.mail.securityapp.auth.app.register.RegisterUseCase;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final RegisterUseCase registerUseCase;
  private final AuthenticateUseCase authenticateUseCase;

  @PostMapping(value = "/login")
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest user) {
    return ResponseEntity.ok(authenticateUseCase.authenticate(user));
  }

  @PostMapping("/register")
  public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest user) {
    return ResponseEntity.status(201).body(registerUseCase.register(user));
  }

  @PostMapping("/verify")
  public ResponseEntity<LoginResponse> verify(@RequestParam String username, @RequestParam String code) {
    return ResponseEntity.ok(registerUseCase.verifyCode(username, code));
  }

}
