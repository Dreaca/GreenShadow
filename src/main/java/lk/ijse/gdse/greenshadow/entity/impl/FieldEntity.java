package lk.ijse.gdse.greenshadow.entity.impl;

import jakarta.persistence.*;
import lk.ijse.gdse.greenshadow.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FieldEntity implements SuperEntity {
    @Id
    private String fieldCode;
    private String fieldName;
    private Point location;
    private Double size;

    @ManyToOne(fetch = FetchType.LAZY) // Use LAZY to optimize performance
    @JoinColumn(name = "crop_code", referencedColumnName = "cropCode")
    private CropEntity crop;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "field_staff",
            joinColumns = @JoinColumn(name = "field_code", referencedColumnName = "fieldCode"),
            inverseJoinColumns = @JoinColumn(name = "staff_id", referencedColumnName = "staffId")
    )
    private List<StaffEntity> staff;

    @Column(columnDefinition = "LONGTEXT")
    private String fieldPicture1;

    @Column(columnDefinition = "LONGTEXT")
    private String fieldPicture2;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "field_equipment",
            joinColumns = @JoinColumn(name = "field_code", referencedColumnName = "fieldCode"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id", referencedColumnName = "equipmentCode")
    )
    private List<EquipmentEntity> equipment;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LogEntity> logs;
}
