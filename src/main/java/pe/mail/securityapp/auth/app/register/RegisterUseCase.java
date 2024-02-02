package pe.mail.securityapp.auth.app.register;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.mail.securityapp.auth.app.login.LoginData;
import pe.mail.securityapp.auth.app.login.LoginResponse;
import pe.mail.securityapp.auth.app.services.JwtService;
import pe.mail.securityapp.auth.domain.exception.InvalidVerificationCode;
import pe.mail.securityapp.auth.domain.exception.VerificationAlreadyUsed;
import pe.mail.securityapp.auth.domain.model.User;
import pe.mail.securityapp.auth.infra.db.repos.IUserRepo;
import pe.mail.securityapp.auth.infra.mail.VerificationEmail;

import java.util.HashMap;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RegisterUseCase {

  private final VerificationEmail sender;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final IUserRepo userRepo;

  public RegisterResponse register(RegisterRequest user) {
    var code = String.valueOf(generateVerificationCode());
    var userWithPasswordHashed = User.builder()
        .email(user.getEmail())
        .password(passwordEncoder.encode(user.getPassword()))
        .phone(user.getPhone())
        .username(user.getUsername())
        .verificationCode(code)
        .build();

    var savedUser = userRepo.save(userWithPasswordHashed);

    sender.sendEmailVerification(
        user.getEmail(),
        "Email Code Verification",
        code
    );

    var data = RegisterData.builder()
        .userId(savedUser.getUserId())
        .email(savedUser.getEmail())
        .phone(savedUser.getPhone())
        .username(savedUser.getUsername())
        .build();

    return RegisterResponse.builder()
        .message("Your data have been created, please verify your email with the verification code sending to " + savedUser.getEmail())
        .status(HttpStatus.CREATED.value())
        .data(data)
        .build();
  }

  public LoginResponse verifyCode(String username, String code) {
    var user = userRepo.findByUsername(username).orElse(null);

    if (user == null) throw new UsernameNotFoundException("User not found");
    if (user.isVerify()) throw new VerificationAlreadyUsed("The code is already verified");
    if (!user.getVerificationCode().equals(code)) throw new InvalidVerificationCode("The code "+ code + " is invalid");

    var userAuthenticated = new UsernamePasswordAuthenticationToken(
        user,
        null,
        user.getAuthorities()
    );

    SecurityContextHolder.getContext().setAuthentication(userAuthenticated);

    var principal = (User) userAuthenticated.getPrincipal();
    var token = jwtService.generateToken(new HashMap<>(), principal);
    var data = LoginData.builder()
        .token(token)
        .username(principal.getUsername())
        .build();

    user.setVerify(true);
    userRepo.save(user);

    return LoginResponse.builder()
        .message("Verification successful")
        .status(HttpStatus.OK.value())
        .data(data)
        .build();
  }

  private int generateVerificationCode() {
    var random = new Random();
    return random.nextInt(900000) + 100000;
  }
}
