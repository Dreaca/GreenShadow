package lk.ijse.gdse.greenshadow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogDTO {
    private String logcode;
    private Date logdate;
    private String observation;
    private String logImage;
    private List<FieldDTO> fields;
    private List<CropDTO> crops;
    private List<StaffDTO> staff;
}
