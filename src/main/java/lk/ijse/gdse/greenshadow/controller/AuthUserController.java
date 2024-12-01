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
    private UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<JWTAuthResponse> signUp(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("designation") String designation,
            @RequestParam("gender") String gender,
            @RequestParam("joinedDate") String joinedDate,
            @RequestParam("dob") String dob,
            @RequestParam("role") String role,
            @RequestParam("address1") String address1,
            @RequestParam("address2") String address2,
            @RequestParam("address3") String address3,
            @RequestParam("address4") String address4,
            @RequestParam("address5") String address5,
            @RequestParam("contactNo") String contactNo,
            @RequestParam("email") String email, // StaffDTO email
            @RequestParam("password") String password // UserDTO password
    ) {
        System.out.println(role);
        StaffDTO member = new StaffDTO();
        UserDTO user = new UserDTO();
        //        Staff Member data
        member.setStaffId(Apputil.generateStaffID());
        member.setFirstName(firstName);
        member.setLastName(lastName);
        member.setDesignation(designation);
        member.setGender(Gender.valueOf(gender.toUpperCase()));
        member.setJoinedDate(Date.valueOf(joinedDate));
        member.setDOB(Date.valueOf(dob));
        member.setAddressLine1(address1);
        member.setAddressLine2(address2);
        member.setAddressLine3(address3);
        member.setAddressLine4(address4);
        member.setAddressLine5(address5);
        member.setContactNo(contactNo);
        member.setEmail(email);
        member.setRole(Role.valueOf(role.toUpperCase()));

        //        User Data
        user.setUserId(Apputil.generateUSerId());
        user.setEmail(email);
        user.setRole(Role.valueOf(role.toUpperCase()));
        user.setPassword(passwordEncoder.encode(password));

        return ResponseEntity.ok(authService.signUp(member, user));
    }
    @PostMapping("/signIn")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody SignIn signIn){
        return ResponseEntity.ok(authService.signIn(signIn));
    }
    @PostMapping("/refreshToken")
    public ResponseEntity<JWTAuthResponse> refreshToken(@RequestBody String refreshToken){
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }
}
