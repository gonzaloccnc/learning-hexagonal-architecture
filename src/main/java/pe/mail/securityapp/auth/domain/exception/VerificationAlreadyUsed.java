package pe.mail.securityapp.auth.domain.exception;

public class VerificationAlreadyUsed extends RuntimeException {

  public VerificationAlreadyUsed(String message) {
    super(message);
  }
}
