package lk.ijse.gdse.greenshadow.dto.impl;

import lk.ijse.gdse.greenshadow.dto.LogStatus;
import lk.ijse.gdse.greenshadow.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogDTO implements LogStatus {
    private String logcode;
    private Date logdate;
    private String observation;
    private String logImage;
    private FieldDTO fields;
    private CropDTO crop;
    private List<StaffDTO> staff;
}
