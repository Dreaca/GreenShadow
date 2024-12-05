package lk.ijse.gdse.greenshadow.entity.impl;

import jakarta.persistence.*;
import lk.ijse.gdse.greenshadow.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class LogEntity implements SuperEntity {
    @Id
    private String logcode;
    private Date logdate;
    private String observation;
    @Column(columnDefinition = "LONGTEXT")
    private String logImage;
    @ManyToOne
    private FieldEntity field;
    @ManyToOne
    private CropEntity crop;
    @ManyToMany
    private List<StaffEntity> staff;
}
