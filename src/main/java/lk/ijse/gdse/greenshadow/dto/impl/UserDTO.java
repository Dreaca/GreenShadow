package lk.ijse.gdse.greenshadow.dto.impl;


import lk.ijse.gdse.greenshadow.dto.SuperDTO;
import lk.ijse.gdse.greenshadow.dto.UserStatus;
import lk.ijse.gdse.greenshadow.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements UserStatus {
    private String email;
    private String password;
    private Role role;
}
