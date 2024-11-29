package lk.ijse.gdse.greenshadow.entity.impl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
    private String logImage;
    @ManyToOne
    private FieldEntity field;
    @ManyToOne
    private CropEntity crop;
    @ManyToMany
    private List<StaffEntity> staff;
}
