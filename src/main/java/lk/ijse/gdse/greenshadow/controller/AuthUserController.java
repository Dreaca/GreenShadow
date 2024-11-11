package lk.ijse.gdse.greenshadow.controller;

import lk.ijse.gdse.greenshadow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthUserController {
    private UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
}
