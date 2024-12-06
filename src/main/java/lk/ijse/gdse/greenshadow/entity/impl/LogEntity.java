package lk.ijse.gdse.greenshadow.entity.impl;

import jakarta.persistence.*;
import lk.ijse.gdse.greenshadow.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LogEntity implements SuperEntity {
    @Id
    private String logCode;
    private Date logDate;
    private String observation;

    @Column(columnDefinition = "LONGTEXT")
    private String logImage;

    @ManyToOne(fetch = FetchType.LAZY) // Use LAZY to avoid unnecessary loading
    @JoinColumn(name = "field_code", referencedColumnName = "fieldCode")
    private FieldEntity field;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crop_code", referencedColumnName = "cropCode")
    private CropEntity crop;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "staff_logs",
            joinColumns = @JoinColumn(name = "log_code", referencedColumnName = "logCode"),
            inverseJoinColumns = @JoinColumn(name = "staff_id", referencedColumnName = "staffId")
    )
    private List<StaffEntity> staff;
}
