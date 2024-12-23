package lk.ijse.gdse.greenshadow.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUsername(String token);
    String generateToken(UserDetails user);
    boolean validateToken(String token, UserDetails user);
    String refreshToken(UserDetails user);
    Claims getClaims(String token);
}
