package lk.ijse.gdse.greenshadow.entity.impl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lk.ijse.gdse.greenshadow.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
//@Entity
public class EquipmentEntity implements SuperEntity {
    @Id
    private String equipmentCode;
    private String name;
    private String type;
    private String status;
    private StaffEntity staff;
    private FieldEntity field;
}
