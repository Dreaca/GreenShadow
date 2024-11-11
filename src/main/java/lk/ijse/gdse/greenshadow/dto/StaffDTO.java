package lk.ijse.gdse.greenshadow.dto;

import lk.ijse.gdse.greenshadow.entity.Gender;
import lk.ijse.gdse.greenshadow.entity.Role;
import lk.ijse.gdse.greenshadow.entity.impl.FieldEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffDTO {
    private String staffId;
    private String firstName;
    private String lastName;
    private String designation;
    private Gender gender;
    private Date joinedDate;
    private Date DOB;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    private String contactNo;
    private String email;
    private Role role;
    private List<FieldDTO> field;
}
