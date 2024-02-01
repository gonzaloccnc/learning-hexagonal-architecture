package pe.mail.securityapp.auth.app.services;

import pe.mail.securityapp.auth.app.login.LoginRequest;
import pe.mail.securityapp.auth.app.login.LoginResponse;
import pe.mail.securityapp.auth.app.register.RegisterRequest;
import pe.mail.securityapp.auth.app.register.RegisterResponse;
import pe.mail.securityapp.auth.domain.model.User;

public interface IAuthService {
  LoginResponse authenticate(LoginRequest user);

  RegisterResponse register(RegisterRequest user);

  User refreshToken(User user, String token);

}
