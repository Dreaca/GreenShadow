package lk.ijse.gdse.greenshadow.controller;

import lk.ijse.gdse.greenshadow.dto.impl.StaffDTO;
import lk.ijse.gdse.greenshadow.dto.impl.UserDTO;
import lk.ijse.gdse.greenshadow.entity.Gender;
import lk.ijse.gdse.greenshadow.entity.Role;
import lk.ijse.gdse.greenshadow.secure.JWTAuthResponse;
import lk.ijse.gdse.greenshadow.secure.SignIn;
import lk.ijse.gdse.greenshadow.service.AuthService;
import lk.ijse.gdse.greenshadow.service.UserService;
import lk.ijse.gdse.greenshadow.util.Apputil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthUserController {
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<JWTAuthResponse> signUp(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        UserDTO user = new UserDTO();

        //        User Data
        user.setEmail(email);
        user.setRole(Role.OTHER);
        user.setPassword(passwordEncoder.encode(password));

        return ResponseEntity.ok(authService.signUp(user));
    }
    @PostMapping("/signIn")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody SignIn signIn){
        return ResponseEntity.ok(authService.signIn(signIn));
    }
    @PostMapping("/refreshToken")
    public ResponseEntity<JWTAuthResponse> refreshToken(@RequestBody String refreshToken){
        System.out.println(refreshToken);
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }
}
