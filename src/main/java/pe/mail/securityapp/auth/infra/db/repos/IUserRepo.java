package pe.mail.securityapp.auth.infra.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.mail.securityapp.auth.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepo extends JpaRepository<User, UUID> {
  Optional<User> findByUsername(String username);
}
