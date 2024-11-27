package lk.ijse.gdse.greenshadow.entity.impl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lk.ijse.gdse.greenshadow.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@AllArgsConstructor
@Data
@NoArgsConstructor
//@Entity
public class LogEntity implements SuperEntity {
    @Id
    private String logcode;
    private Date logdate;
    private String observation;
    private String logImage;
    private List fields;
    private List crops;
    private List staff;
}
