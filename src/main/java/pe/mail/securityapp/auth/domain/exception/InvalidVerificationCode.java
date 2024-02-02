package pe.mail.securityapp.auth.domain.exception;

public class InvalidVerificationCode extends RuntimeException {
  public InvalidVerificationCode(String message) {
    super(message);
  }
}
