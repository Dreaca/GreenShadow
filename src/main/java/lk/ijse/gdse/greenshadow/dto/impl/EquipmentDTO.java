package lk.ijse.gdse.greenshadow.dto.impl;

import lk.ijse.gdse.greenshadow.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentDTO implements SuperDTO {
    private String equipmentCode;
    private String name;
    private String type;
    private String status;
    private StaffDTO staff;
    private FieldDTO field;
}
