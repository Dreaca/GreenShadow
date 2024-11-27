package lk.ijse.gdse.greenshadow.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private StaffDTO staffDTO;
    private UserDTO userDTO;
}
