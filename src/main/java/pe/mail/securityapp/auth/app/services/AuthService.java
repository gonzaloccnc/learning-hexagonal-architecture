package pe.mail.securityapp.auth.app.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.mail.securityapp.auth.app.login.LoginData;
import pe.mail.securityapp.auth.app.login.LoginRequest;
import pe.mail.securityapp.auth.app.login.LoginResponse;
import pe.mail.securityapp.auth.app.register.RegisterData;
import pe.mail.securityapp.auth.app.register.RegisterRequest;
import pe.mail.securityapp.auth.app.register.RegisterResponse;
import pe.mail.securityapp.auth.domain.model.User;
import pe.mail.securityapp.auth.infra.db.repos.IUserRepo;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

  private final AuthenticationManager manager;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final IUserRepo userRepo;

  @Override
  public LoginResponse authenticate(LoginRequest user) {
    var userAuthenticated = manager.authenticate(
        new UsernamePasswordAuthenticationToken(
            user.getUsername(),
            user.getPassword()
        )
    );

    var principal = (User) userAuthenticated.getPrincipal();
    var token = jwtService.generateToken(new HashMap<>(), principal);
    var data = LoginData.builder()
        .token(token)
        .username(principal.getUsername())
        .build();

    return LoginResponse.builder()
        .message("User authenticated successful")
        .url("")
        .status(HttpStatus.OK.value())
        .data(data)
        .build();
  }

  @Override
  public RegisterResponse register(RegisterRequest user) {
    var userWithPasswordHashed = User.builder()
        .email(user.getEmail())
        .password(passwordEncoder.encode(user.getPassword()))
        .phone(user.getPhone())
        .username(user.getUsername())
        .build();

    var savedUser = userRepo.save(userWithPasswordHashed);



    var data = RegisterData.builder()
        .userId(savedUser.getUserId())
        .email(savedUser.getEmail())
        .phone(savedUser.getPhone())
        .username(savedUser.getUsername())
        .token("")
        .build();

    return RegisterResponse.builder()
        .message("User created successful")
        .status(HttpStatus.CREATED.value())
        .url("")
        .data(data)
        .build();
  }

  @Override
  public User refreshToken(User user, String token) {
    return null;
  }
}
