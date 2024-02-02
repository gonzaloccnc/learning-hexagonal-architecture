package pe.mail.securityapp.auth.app.login;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor@NoArgsConstructor
@Builder
public class LoginRequest {

  @Size(min = 5, max = 25)
  @Pattern(regexp = "^[a-zA-Z_]*$")
  private String username;

  @Size(min = 8, max = 20)
  private String password;
}
