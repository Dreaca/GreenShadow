package lk.ijse.gdse.greenshadow.service.impl;

import lk.ijse.gdse.greenshadow.service.JwtService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {
    @Override
    public String extractUsername(String token) {
        return "";
    }

    @Override
    public String generateToken(UserDetails user) {
        return "";
    }

    @Override
    public boolean validateToken(String token, UserDetails user) {
        return false;
    }

    @Override
    public String refreshToken(UserDetails user) {
        return "";
    }
}
