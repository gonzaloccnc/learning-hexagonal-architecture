package pe.mail.securityapp.auth.app.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public final class LoginData {
  private String username;
  private String token;
}
