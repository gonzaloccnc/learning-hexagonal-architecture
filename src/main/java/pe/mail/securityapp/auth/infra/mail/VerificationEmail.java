package pe.mail.securityapp.auth.infra.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationEmail {

  private final JavaMailSender mailSender;

  public void sendEmailVerification(String to, String subject, String verificationCode) {

    String templateHTML = "<h1>Verificación de Email</h1>"
        + "<p>Dear user,</p>"
        + "<p>Thank you for registering on our website. Please verify your email by entering the following security code:</p>"
        + "<h2 style='color:blue'>" + verificationCode + "</h2>"
        + "<p>If you did not request this email, please ignore it.</p>"
        + "<p>Regards!</p>";

    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

    try {
      helper.setText(templateHTML, true);
      helper.setSubject(subject);
      helper.setFrom("noreply@developer.app.cha");
      helper.setTo(to);
      this.mailSender.send(mimeMessage);
    } catch (MailException | MessagingException ex) {
      System.out.println(ex.getMessage());
    }

  }
}
