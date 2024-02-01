package pe.mail.securityapp.todos.infra.input.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

  @GetMapping
  public ResponseEntity<String> greeting() {
    return ResponseEntity.ok("Hello protected route");
  }

}
