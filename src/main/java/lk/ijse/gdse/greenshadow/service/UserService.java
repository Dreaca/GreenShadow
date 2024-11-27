package lk.ijse.gdse.greenshadow.service;

import lk.ijse.gdse.greenshadow.dto.UserStatus;
import lk.ijse.gdse.greenshadow.dto.impl.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    void saveUser(UserDTO user);
    UserDetailsService getUserDetailsService();
    List<UserDTO> getAllUsers();
    UserStatus getUser(String userId);
    void deleteUser(String userId);
    void updateUser(String userId, UserDTO user);

}
