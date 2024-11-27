package lk.ijse.gdse.greenshadow.entity.impl;

import jakarta.persistence.*;
import lk.ijse.gdse.greenshadow.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.List;

//@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldEntity implements SuperEntity {
    @Id
    private String fieldCode;
    private String fieldName;
    private Point location;
    private Double extSizeofField;
    @OneToMany(mappedBy = "field")
    private List<CropEntity> crops;
    @ManyToMany(mappedBy = "field")
    private List<StaffEntity> staff;
    @Column(columnDefinition = "LONGTEXT")
    private String fieldPicture1;
    @Column(columnDefinition = "LONGTEXT")
    private String fieldPicture2;
}
