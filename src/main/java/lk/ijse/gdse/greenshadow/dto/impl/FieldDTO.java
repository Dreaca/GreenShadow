package lk.ijse.gdse.greenshadow.dto.impl;

import lk.ijse.gdse.greenshadow.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldDTO implements SuperDTO {
    private String fieldCode;
    private String fieldName;
    private Point location;
    private Double extSizeofField;
    private List<CropDTO> crops;
    private List<StaffDTO> staff;
    private String fieldPicture1;
    private String fieldPicture2;
}
