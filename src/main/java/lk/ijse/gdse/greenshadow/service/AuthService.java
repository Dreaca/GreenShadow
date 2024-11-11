package lk.ijse.gdse.greenshadow.service;

import lk.ijse.gdse.greenshadow.dto.StaffDTO;
import lk.ijse.gdse.greenshadow.secure.JWTAuthResponse;
import lk.ijse.gdse.greenshadow.secure.SignIn;

public interface AuthService {
    JWTAuthResponse signIn(SignIn signIn);
    JWTAuthResponse signUp(StaffDTO staffDTO);
    JWTAuthResponse refreshToken(String refreshToken);
}
