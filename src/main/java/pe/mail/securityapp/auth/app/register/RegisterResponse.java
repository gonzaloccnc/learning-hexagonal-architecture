package pe.mail.securityapp.auth.app.register;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import pe.mail.securityapp.auth.domain.model.User;
import pe.mail.securityapp.share.ApiResponse;

@SuperBuilder @Getter
public class RegisterResponse extends ApiResponse<RegisterData> {
}
