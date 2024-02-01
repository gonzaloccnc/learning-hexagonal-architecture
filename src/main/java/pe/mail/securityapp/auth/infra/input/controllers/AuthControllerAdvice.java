package pe.mail.securityapp.auth.infra.input.controllers;

import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class AuthControllerAdvice {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<?> violationConstraint(MethodArgumentNotValidException ex) {
    // make the return apiResponse
    return ResponseEntity.status(400).body(getErrors(ex.getBindingResult()));
  }

  @ExceptionHandler(SignatureException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public Map<String, String> signatureException(SignatureException ex) {
    var map = new HashMap<String, String>();
    map.put("error", ex.toString());
    map.put("message", ex.getMessage());
    return map;
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Map<String, String> generalException(Exception ex) {
    var map = new HashMap<String, String>();
    map.put("message", ex.getMessage());
    map.put("error", ex.toString());
    return map;
  }

  private Map<String, String> getErrors(BindingResult br) {
    return br.getFieldErrors().stream()
        .collect(Collectors.groupingBy(
            FieldError::getField,
            Collectors.mapping(
                FieldError::getDefaultMessage,
                Collectors.joining(", ")
            )
        ));
  }
}
