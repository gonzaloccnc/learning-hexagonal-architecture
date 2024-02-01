package pe.mail.securityapp.share;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import java.util.Map;

@SuperBuilder @Getter
public abstract class ApiResponse<T> {
  protected String message;
  protected int status;
  protected Map<String, String> errors;
  protected T data;
  protected String url;
}
