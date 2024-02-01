package pe.mail.securityapp.auth.app.login;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import pe.mail.securityapp.share.ApiResponse;

@SuperBuilder @Getter
public class LoginResponse extends ApiResponse<LoginData> {
}
