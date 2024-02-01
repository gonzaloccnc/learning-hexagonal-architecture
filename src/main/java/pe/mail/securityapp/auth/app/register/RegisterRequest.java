package pe.mail.securityapp.auth.app.register;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Builder
public class RegisterRequest {

  @Size(min = 5, max = 25)
  @Pattern(regexp = "^[a-zA-Z_]*$", message = "The username should contains letters and '_'")
  private String username;

  @Size(min = 8, max = 20)
  private String password;

  @Email @NotNull @NotEmpty @Size(max = 100)
  private String email;

  @NotNull @NotEmpty @Size(min = 9, max = 9)
  private String phone;

}
