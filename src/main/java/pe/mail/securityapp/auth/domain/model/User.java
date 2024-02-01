package pe.mail.securityapp.auth.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

@Entity(name = "users")
@AllArgsConstructor @NoArgsConstructor
@Builder @Data
public class User implements UserDetails {

  @Id @GeneratedValue(strategy = GenerationType.UUID)
  private UUID userId;

  @Column(length = 25, unique = true)
  private String username;

  @Column
  private String password;

  @Column(length = 100, unique = true)
  private String email;

  @Column(unique = true)
  private String phone;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
