package pe.mail.securityapp.auth.app.register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data @AllArgsConstructor
@NoArgsConstructor @Builder
public final class RegisterData {
  private UUID userId;
  private String username;
  private String email;
  private String phone;
}
