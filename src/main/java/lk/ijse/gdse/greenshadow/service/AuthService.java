package lk.ijse.gdse.greenshadow.service;

import lk.ijse.gdse.greenshadow.dto.impl.StaffDTO;
import lk.ijse.gdse.greenshadow.dto.impl.UserDTO;
import lk.ijse.gdse.greenshadow.secure.JWTAuthResponse;
import lk.ijse.gdse.greenshadow.secure.SignIn;

public interface AuthService {
    JWTAuthResponse signIn(SignIn signIn);
    JWTAuthResponse signUp(StaffDTO staffDTO, UserDTO userDTO);
    JWTAuthResponse refreshToken(String accessToken);
}
