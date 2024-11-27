package lk.ijse.gdse.greenshadow.service.impl;

import lk.ijse.gdse.greenshadow.dao.StaffDao;
import lk.ijse.gdse.greenshadow.dao.UserDao;
import lk.ijse.gdse.greenshadow.dto.impl.StaffDTO;
import lk.ijse.gdse.greenshadow.dto.impl.UserDTO;
import lk.ijse.gdse.greenshadow.entity.impl.StaffEntity;
import lk.ijse.gdse.greenshadow.entity.impl.UserEntity;
import lk.ijse.gdse.greenshadow.secure.JWTAuthResponse;
import lk.ijse.gdse.greenshadow.secure.SignIn;
import lk.ijse.gdse.greenshadow.service.AuthService;
import lk.ijse.gdse.greenshadow.service.JwtService;
import lk.ijse.gdse.greenshadow.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceIMPL implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDao userDao;
    private final JwtService jwtService;
    private final StaffDao staffDao;
    private final Mapping mapping;

    @Override
    public JWTAuthResponse signIn(SignIn signIn) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword()));
        UserEntity user = userDao.findByEmail(signIn.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String generateToken = jwtService.generateToken(user);
        return JWTAuthResponse.builder().token(generateToken).build();
    }

    @Override
    public JWTAuthResponse signUp(StaffDTO staffDTO, UserDTO userDTO) {
        UserEntity save = userDao.save(mapping.toUserEntity(userDTO));
        StaffEntity savedStaff = staffDao.save(mapping.toStaffEntity(staffDTO));
        if(savedStaff != null) {
            String generateToken = jwtService.generateToken(save);
            return JWTAuthResponse.builder().token(generateToken).build();
        }else return JWTAuthResponse.builder().build();


    }

    @Override
    public JWTAuthResponse refreshToken(String accessToken) {
        String username = jwtService.extractUsername(accessToken);
        UserEntity user = userDao.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String refreshToken = jwtService.refreshToken(user);
        return JWTAuthResponse.builder().token(refreshToken).build();
    }
}
