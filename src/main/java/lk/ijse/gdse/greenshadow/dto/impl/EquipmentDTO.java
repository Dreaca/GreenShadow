package lk.ijse.gdse.greenshadow.dto.impl;

import lk.ijse.gdse.greenshadow.dto.EquipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentDTO implements EquipmentStatus {
    private String equipmentCode;
    private String name;
    private String type;
    private String status;
    private List<StaffDTO> staffList;
    private List<FieldDTO> fieldList;
}
