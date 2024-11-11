package lk.ijse.gdse.greenshadow.dto;

import lk.ijse.gdse.greenshadow.entity.impl.FieldEntity;
import lk.ijse.gdse.greenshadow.entity.impl.StaffEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentDTO {
    private String equipmentCode;
    private String name;
    private String type;
    private String status;
    private StaffDTO staff;
    private FieldDTO field;
}
