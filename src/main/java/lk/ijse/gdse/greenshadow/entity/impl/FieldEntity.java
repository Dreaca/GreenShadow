package lk.ijse.gdse.greenshadow.entity.impl;

import jakarta.persistence.*;
import lk.ijse.gdse.greenshadow.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.awt.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldEntity implements SuperEntity {
    @Id
    private String fieldCode;
    private String fieldName;
    private Point location;
    private Double extSizeofField;
    @ManyToOne
    @JoinColumn(name = "cropCode", nullable = false)
    private CropEntity crop;
    @ManyToMany
    private List<StaffEntity> staff;
    @Column(columnDefinition = "LONGTEXT")
    private String fieldPicture1;
    @Column(columnDefinition = "LONGTEXT")
    private String fieldPicture2;
    @ManyToMany
    private List<EquipmentEntity>equipment;
    @OneToMany
    private List<LogEntity> logs;
}
