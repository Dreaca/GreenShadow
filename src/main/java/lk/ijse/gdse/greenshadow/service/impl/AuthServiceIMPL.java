package lk.ijse.gdse.greenshadow.service.impl;

import lk.ijse.gdse.greenshadow.dao.StaffDao;
import lk.ijse.gdse.greenshadow.dto.StaffDTO;
import lk.ijse.gdse.greenshadow.secure.JWTAuthResponse;
import lk.ijse.gdse.greenshadow.secure.SignIn;
import lk.ijse.gdse.greenshadow.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.spi.Mapping;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceIMPL implements AuthService {
    private final StaffDao staffDao;
    private final Mapping mapping;


    @Override
    public JWTAuthResponse signIn(SignIn signIn) {
        return null;
    }

    @Override
    public JWTAuthResponse signUp(StaffDTO staffDTO) {
        return null;
    }

    @Override
    public JWTAuthResponse refreshToken(String refreshToken) {
        return null;
    }
}
