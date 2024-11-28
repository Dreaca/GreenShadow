package lk.ijse.gdse.greenshadow.entity.impl;

import jakarta.persistence.*;
import lk.ijse.gdse.greenshadow.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class EquipmentEntity implements SuperEntity {
    @Id
    private String equipmentCode;
    private String name;
    private String type;
    private String status;
    @OneToMany(mappedBy = "equipment")
    private List<StaffEntity> staff;
    @ManyToMany
    private List<FieldEntity> field;
}
