package pe.mail.securityapp.auth.app.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import pe.mail.securityapp.auth.domain.model.User;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

  private final static String SECRET = "hUdhAhoAmZVwAriqd8B4UdhAhoAmZVwAriqd8B47bAi.$2a$10$c83ZkPp2fi5FXan4bJNLN.ePY8da";
  private final static long ACCESS_TOKEN_SECONDS = 1_296_000L;

  public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = verifyToken(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(Map<String, Object> claims, User user) {
    Date expirationDate = new Date(System.currentTimeMillis() + ACCESS_TOKEN_SECONDS * 1000);

    return Jwts.builder()
        .addClaims(claims)
        .setSubject(user.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(expirationDate)
        .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS256)
        .compact();
  }

  private Claims verifyToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(SECRET.getBytes())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

}
