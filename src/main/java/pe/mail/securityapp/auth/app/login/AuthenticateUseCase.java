package pe.mail.securityapp.auth.app.login;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import pe.mail.securityapp.auth.app.services.JwtService;
import pe.mail.securityapp.auth.domain.exception.UnverifiedException;
import pe.mail.securityapp.auth.domain.model.User;
import pe.mail.securityapp.auth.infra.db.repos.IUserRepo;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticateUseCase {

  private final AuthenticationManager manager;
  private final JwtService jwtService;
  private final IUserRepo userRepo;
  public LoginResponse authenticate(LoginRequest user) {
    var userFound = userRepo.findByUsername(user.getUsername()).orElse(null);

    if (userFound == null || !userFound.isVerify())
      throw new UnverifiedException("Please check the user exist or verify your email");

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
        .status(HttpStatus.OK.value())
        .data(data)
        .build();
  }
}
